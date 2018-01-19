package com.example.android.introvert.Editors;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.introvert.Activities.NoteActivity;
import com.example.android.introvert.Notes.Note;
import com.example.android.introvert.R;
import com.example.android.introvert.Utils.FileUtils;
import com.example.android.introvert.Utils.FormatUtils;

import java.io.File;
import java.io.IOException;


/**
 * Created by takeoff on 010 10 Jan 18.
 */

public class AudioEditor extends RelativeLayout implements MyEditor {

    private final String TAG = "INTROWERT_AUDIO_EDITOR";

    // for interface methods
    private int editorType = 0; // Should be 2 (audio)
    private int editorRole = 0; // 1 - content, 2 - tags, 3 - comment
    private boolean exists; // Whether this note has content
    private Note note;
    private NoteActivity noteActivity;

    // Editor container
    LinearLayout editorContainer;

    // Currently displaying view of the editor
    View currentView;

    // UI elements: empty mode
    TextView emptyRecHintTextView;
    Button emptyRecStopButton;
    TextView emptyTimeTextView;
    SeekBar emptySeekBar; // Just a space holder

    // UI elements: non empty mode
    Button recButton;
    Button playPauseButton;
    Button stopButton;
    TextView timeTextView;
    SeekBar seekBar;

    // Empty and non empty mode layouts
    int emptyModeLayout = R.layout.editor_audio_empty;
    int nonEmptyModeLayout = R.layout.editor_audio;
    boolean emptyMode = true;

    // Folder and file names
    String fileName;
    String fileExtension = ".3gpp";
    String destinationFolder;
    String destinationFilePath;
    File destinationFile;

    // Player/recorder
    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;

    // Settings
    boolean autoStartRecording;
    // Audio settings
    int audioSource;
    int outputFormat;
    int audioEncoder;
    int audioChannels;
    int bitRate;
    int samplingRate;
    int maxDuration;
    long maxFileSize;

    // File duration
    int fileDuration = 0;
    int fileCurrentPosition = 0;
    String fileDurationFormatted;

    // Editor states
    boolean isRecording;
    boolean isPlaying;
    boolean isPaused;

    Handler handler = new Handler();
    Runnable runnable;


    /* Basic required constructor */
    public AudioEditor(Context context) {
        super(context);
    }


    /* Regular constructor */
    public AudioEditor(LinearLayout editorContainer, int editorType, int editorRole, boolean exists,
                       Note note, NoteActivity noteActivity) {
        this(noteActivity);

        this.editorContainer = editorContainer;
        this.editorType = editorType;
        this.editorRole = editorRole;
        this.exists = exists;
        this.note = note;
        this.noteActivity = noteActivity;

        prepareFile();

        // Initialize appropriate layout and its components
        if (exists) initNonEmptyModeComponents(); // Current note has previous content
        else initEmptyModeComponents(); // Current note has no previous content
    }


    /* Initialize empty mode layout and its components */
    private void initEmptyModeComponents() {
        // Set fresh layout
        setEditorLayout(emptyModeLayout);

        // Init UI elements
        emptyRecHintTextView = (TextView) findViewById(R.id.editor_audio_empty_record_hint_tv);
        emptyRecStopButton = (Button) findViewById(R.id.editor_audio_empty_rec_stop_b);
        emptyTimeTextView = (TextView) findViewById(R.id.editor_audio_empty_time);
        emptySeekBar = (SeekBar) findViewById(R.id.editor_audio_empty_seekbar);

        // Hide seekbar as it is just a placeholder
        emptySeekBar.setVisibility(INVISIBLE);

        // Set onclick listeners
        emptyRecStopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording) { // Start recording
                    recordStart();
                } else { // Stop recording
                    recordStop();
                    initNonEmptyModeComponents();
                }
            }
        });

        getAutoRecordSetting();

        if (autoStartRecording) {
            recordStart();
        }
        // Update UI elements to corresponding state
        updateRecordingUI();

    }


    /* Initialize non mode layout and its components */
    private void initNonEmptyModeComponents() {
        // Set fresh layout
        setEditorLayout(nonEmptyModeLayout);

        // Init UI elements
        recButton = (Button) findViewById(R.id.editor_audio_record_b);
        playPauseButton = (Button) findViewById(R.id.editor_audio_play_pause_b);
        stopButton = (Button) findViewById(R.id.editor_audio_stop_b);
        timeTextView = (TextView) findViewById(R.id.editor_audio_time);
        seekBar = (SeekBar) findViewById(R.id.editor_audio_empty_seekbar);

        // Set onclick listeners
        recButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initEmptyModeComponents();
                recordStart();
            }
        });

        playPauseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying && !isPaused) { // Playback is stopped: Start playback
                    playStart();
                    updatePlayingUI();
                } else if (isPlaying && !isPaused) { // Playback is on: pause playback
                    playPause();
                    updatePlayingUI();
                } else { // Playback is paused: continue playback
                    playContinue();
                    updatePlayingUI();
                }
            }
        });

        stopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playStop();
                updatePlayingUI();
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }
        });

        prepareMediaPlayer();
        // Update UI elements to corresponding state
        updatePlayingUI();
    }


    /* Sets the layout for editor. Either editor_audio_empty.xml or editor_audio.xml */
    private void setEditorLayout(int layout) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null) { // Android makes me check this

            // Check if there is a set layout
            if (currentView != null) { // There is a set layout
                editorContainer.removeView(currentView);
                removeAllViewsInLayout();
                View newView = inflater.inflate(layout, this);
                editorContainer.addView(newView);
                Log.i(TAG, "Updated layout");
                currentView = newView;
            } else { // No previously set layout
                currentView = inflater.inflate(layout, this);
                Log.i(TAG, "Set new layout");
            }
            setEditorLayoutMode(layout); // Update mode
        }
    }


    /* Sets layout mode boolean corresponding to current layout */
    private void setEditorLayoutMode(int layout) {
        if (layout == emptyModeLayout) {
            emptyMode = true;
        } else if (layout == nonEmptyModeLayout) {
            emptyMode = false;
        } else {
            Log.e(TAG, "Unknown layout: " + layout);
        }
    }


    /* Updates latest location vars and prepares a file based on them */
    private void prepareFile() {
        destinationFolder = FileUtils.getPathForInputType(editorType, note.getTypeId());
        fileName = note.getUpdatedName() + fileExtension;
        destinationFilePath = destinationFolder + "/" + fileName;
        destinationFile = FileUtils.makeFileForPath(destinationFilePath);
    }


    /* Gets current auto-record setting */
    private void getAutoRecordSetting() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(noteActivity);

        autoStartRecording = sharedPreferences.getBoolean
                ("preferences_main_auto_start_recording", true);
    }


    /* Gets audio settings from Preferences and updates corresponding var */
    private void prepareAudioSettings() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(noteActivity);

        autoStartRecording = sharedPreferences.getBoolean
                ("preferences_main_auto_start_recording", true);
        audioSource = Integer.parseInt(sharedPreferences.getString
                ("preferences_main_audio_audio_source", "0"));
        Log.i(TAG, "preferences_main_audio_audio_source: " + audioSource);
        outputFormat = Integer.parseInt(sharedPreferences.getString
                ("preferences_main_audio_output_format", "0"));
        Log.i(TAG, "preferences_main_audio_output_format: " + outputFormat);
        audioEncoder = Integer.parseInt(sharedPreferences
                .getString("preferences_main_audio_audio_encoder", "0"));
        Log.i(TAG, "preferences_main_audio_audio_encoder: " + audioEncoder);
    }


    private Runnable getRunnable() {
        return runnable = new Runnable() {
            @Override
            public void run() {
                if (isPlaying) {
                    Log.i(TAG, "Running while playing..");
                    updatePlayingUI();
                    handler.postDelayed(this, 200);
                } else if (isRecording) {
                    Log.i(TAG, "Running while recording..");
                    updateRecordingUI();
                    fileDuration += 1000;
                    handler.postDelayed(this, 1000);
                }
            }
        };
    }

    private void cancelRunnable() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~PLAYER API~~~~~~~~~~~~~~~~~~~~~~~~*/
    /* Creates MediaRecorder, sets its settings and onCompletion listener*/
    private void prepareMediaPlayer() {
        // Release existing player
        releasePlayer();

        // Get latest settings
        prepareAudioSettings();


        // Prepare player
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                fileCurrentPosition = mediaPlayer.getCurrentPosition();
                fileDuration = mediaPlayer.getDuration();
                fileDurationFormatted = FormatUtils.msToMinsAndSecs(fileDuration);
                seekBar.setMax(fileDuration);
                updatePlayingUI();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlaying = false;
                isPaused = false;
                cancelRunnable();
                updatePlayingUI();
            }
        });
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        prepareFile();
        try {
            mediaPlayer.setDataSource(destinationFilePath);
        } catch (IOException e) {
            Log.e(TAG, "File for media player not found");
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.e(TAG, "Preparation of media player failed");
        }
    }


    /*Frees the player if it exists*/
    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    private void playStart() {
        try {
            prepareMediaPlayer();
            mediaPlayer.start();
            isPlaying = true;
            isPaused = false;
            noteActivity.runOnUiThread(getRunnable());
            Log.i(TAG, "Started playing file: " + destinationFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void playPause() {
        mediaPlayer.pause();
        Log.i(TAG, "Paused playing file: " + destinationFile);
        isPlaying = false;
        isPaused = true;
        cancelRunnable();

    }


    private void playContinue() {
        mediaPlayer.start();
        isPlaying = true;
        isPaused = false;
        noteActivity.runOnUiThread(getRunnable());
    }


    private void playStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            cancelRunnable();
            Log.i(TAG, "Stopped playing file: " + destinationFile);
            isPlaying = false;
            isPaused = false;
        }
    }


    /* Sets UI elements to correspond the state */
    private void updatePlayingUI() {
        Log.i(TAG, "Updating playing UI");

        // Update scroll position
        if (!isPlaying && !isPaused) { // We are stopped
            fileCurrentPosition = 0;
        } else if (isPlaying) { // We are playing
            fileCurrentPosition = mediaPlayer.getCurrentPosition();
        }
        seekBar.setProgress(fileCurrentPosition);

        // Update time display
        String timeDisplay = FormatUtils.msToMinsAndSecs(fileCurrentPosition) + "/"
                + fileDurationFormatted;
        timeTextView.setText(timeDisplay);

        // Update Play button text
        if (isPlaying) { // Set playing UI to playing state
            playPauseButton.setText(R.string.audio_editor_button_pause);
        } else { // Set playing UI to NOT playing state
            playPauseButton.setText(R.string.audio_editor_button_play);
        }
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~RECORDER API~~~~~~~~~~~~~~~~~~~~~~~~*/
    /* Creates MediaRecorder and sets its settings*/
    private void prepareMediaRecorder() {
        // Release existing recorder
        releaseRecorder();

        // Get latest settings
        prepareAudioSettings();

        // Prepare recorder
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(audioSource);
        mediaRecorder.setOutputFormat(outputFormat);
        mediaRecorder.setOutputFile(destinationFilePath);
        mediaRecorder.setAudioEncoder(audioEncoder);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "Preparation of media recorder failed:" + e);
        }
    }


    /*Frees the recorder if exists*/
    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }


    /* Starts recording after creating folder path, deleting existing file, updates UI */
    public void recordStart() {
        //make sure folder for file exists
        FileUtils.mkDirs(destinationFile);

        // delete existing file
        if (destinationFile.exists()) {
            Log.i(TAG, "Deleting existing file: " + destinationFile + ": " +
                    destinationFile.delete());
        }

        prepareMediaRecorder();

        try {
            mediaRecorder.start();
            Log.i(TAG, "Started recording to file: " + destinationFile);
            isRecording = true;
            noteActivity.runOnUiThread(getRunnable());
            updateRecordingUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* Stops recording, resets recorder, updates UI */
    public void recordStop() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            Log.i(TAG, "Stopped recording to file: " + destinationFile);
            isRecording = false;
            cancelRunnable();
            updateRecordingUI();
            mediaRecorder.reset();
        }
    }


    /* Sets UI elements to correspond the state */
    private void updateRecordingUI() {
        Log.i(TAG, "Updating recording UI");

        // Update time display
        String timeDisplay = FormatUtils.msToMinsAndSecs(fileDuration);
        emptyTimeTextView.setText(timeDisplay);


        if (isRecording) { // Set recording UI to recording state
            emptyRecHintTextView.setText(R.string.audio_editor_empty_tv_recording);
            emptyRecHintTextView.setTextColor(Color.RED);
            emptyRecStopButton.setText(R.string.audio_editor_button_stop);
            emptyRecStopButton.setTextColor(Color.BLACK);
        } else { // Set recording UI to NOT recording state
            emptyRecHintTextView.setText(R.string.audio_editor_empty_tv_press_record);
            emptyRecHintTextView.setTextColor(Color.BLACK);
            emptyRecStopButton.setText(R.string.audio_editor_button_rec);
            emptyRecStopButton.setTextColor(Color.RED);
        }
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~INTERFACE API~~~~~~~~~~~~~~~~~~~~~~~~*/
    @Override
    public void setContent(String content) {

     /*   switch (editorRole) {
            case 1: // Content
                note.setUpdatedContent(content);
                break;
            case 2: // Tags
                note.setUpdatedTags(content);
                break;
            case 3: // Comment
                note.setUpdatedComment(content);
                break;
            default: // Something is wrong
                Log.e(TAG, "Error: editor role could not be defined when setting content");
        }*/
    }

    @Override
    public String getContent() {
        return destinationFilePath;
        // TODO: 019 19 Jan 18 return full path or just file name? 
    }

    @Override
    public int getEditorType() {
        return editorType;
    }

    @Override
    public int getEditorRole() {
        return editorRole;
    }

    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public boolean deleteEditor() {
        releasePlayer();
        releaseRecorder();

        return true;
    }

}
