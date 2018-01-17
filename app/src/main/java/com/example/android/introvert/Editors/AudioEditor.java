package com.example.android.introvert.Editors;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
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

import java.io.File;


/**
 * Created by takeoff on 010 10 Jan 18.
 */

public class AudioEditor extends RelativeLayout implements MyEditor {

    // TODO: 017 17 Jan 18 auto-record mode

    private final String TAG = "INTROWERT_AUDIO_EDITOR";

    // for interface methods
    private int editorType = 0; // Should be 2 (audio)
    private int editorRole = 0; // 1 - content, 2 - tags, 3 - comment
    private boolean exists; // Whether this note has content
    private Note note;
    private NoteActivity noteActivity;

    // Player/recorder
    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;

    // Editor container
    LinearLayout editorContainer;

    // Currently displaying view of the editor
    View currentView;

    // UI elements: empty mode
    TextView emptyRecHintTextView;
    Button emptyRecStopButton;
    TextView emptyTimeTextView;
    SeekBar emptySeekBar; // Just for holding space

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


    final String DIR_SD = "Introvert";
    String content = Environment.getExternalStorageDirectory() + "/" + DIR_SD + "/record.3gpp";

/*
    Handler handler;
    Runnable runnable;
    */


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
                initNonEmptyModeComponents();
            }
        });

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
            }
        });
    }


    /* Initialize layout and its components */
    private void initComponents() {
// Init empty mode components


        // Set appropriate initial layout
        if (exists) setEditorLayout(nonEmptyModeLayout);
        else setEditorLayout(emptyModeLayout);

        //Init UI elements
        playPauseButton = (Button) findViewById(R.id.editor_audio_play_pause_b);
        playPauseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playStart();
            }
        });
        stopButton = (Button) findViewById(R.id.editor_audio_stop_b);
        stopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playStop();
            }
        });
        recButton = (Button) findViewById(R.id.editor_audio_record_b);
        recButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                recordStart();
            }
        });
        //seekBar = (SeekBar) findViewById(R.id.editor_audio_seekbar);
        timeTextView = (TextView) findViewById(R.id.editor_audio_time);


        Log.i(TAG, "Creating folder for audio:"
                + FileUtils.makeDirsForInputType("audio", note.getTypeId()));

            /*            sdPath = Environment.getExternalStorageDirectory();
            sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
            // создаем каталог
            sdPath.mkdirs();

            Log.i (TAG, var);*/


        //handler = new Handler();


//        mediaPlayer = MediaPlayer.create(context, R.raw.voice37);


        // React to seekbar changes by user
/*        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress); // * 1000
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/


 /*       mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());
                playCycle();
                mediaPlayer.start();
            }
        });*/


    }


    /**
     * Sets the layout for editor. Either editor_audio_empty.xml or editor_audio.xml
     */
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


    private void setEditorLayoutMode(int layout) {
        if (layout == emptyModeLayout) {
            emptyMode = true;
        } else if (layout == nonEmptyModeLayout) {
            emptyMode = false;
        } else {
            Log.e(TAG, "Unknown layout: " + layout);
        }
    }


    public void playStart() {
        try {
            deleteEditor(); // Release the player
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(content);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void playStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }


    public void recordStart() {
        try {
            deleteEditor(); // Release the recorder

            File outFile = new File(content);
            if (outFile.exists()) {
                outFile.delete();
            }

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(content);
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void recordStop() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
        }
    }


/*    public void playCycle() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                }
                handler.postDelayed(this, 1000);
            }
        });*/


        /*seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if (mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
            handler.postDelayed(runnable, 1000);
        }*/


    /*public void start() {
        mediaPlayer = MediaPlayer.create(context, R.raw.voice37);
        seekBar.setMax(mediaPlayer.getDuration());

        mediaPlayer.start();*/


        /*mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(DATA_SD);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.prepare();
        mediaPlayer.start();*/
    /*}*/


    /* Interface methods */
    @Override
    public void setContent(String content) {

        switch (editorRole) {
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
        }
    }

    @Override
    public String getContent() {
        return null;
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
        // release player
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // release recorder
        if (mediaRecorder != null) { //
            mediaRecorder.release();
            mediaRecorder = null;
        }

        return true;
    }
}
