package com.example.android.introvert.Editors;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.android.introvert.Notes.Note;
import com.example.android.introvert.R;

import java.io.File;


/**
 * Created by takeoff on 010 10 Jan 18.
 */

public class AudioEditor extends RelativeLayout implements MyEditor {

    String TAG = "INTROWERT_AUDIO_EDITOR:";

    MediaPlayer mediaPlayer;
    MediaRecorder mediaRecorder;

    Handler handler;
    Runnable runnable;

    final String DIR_SD = "Introvert";
    final String FILENAME_SD = "fileSD";

    File sdPath;

    String content = Environment.getExternalStorageDirectory() + "/" + DIR_SD + "/record.3gpp";

    private Button playButton;
    private Button stopButton;
    private Button recButton;
    private Button recStopButton;
    private SeekBar seekBar;

    Context context;
    Activity activity;

    private int type; // 1 - content, 2 - tags, 3 - comment

    public AudioEditor(Context context) {
        super(context);
    }

    public AudioEditor(LinearLayout editorContainer, int editorType, int editorRole, boolean exists,
                       Note note, Activity activity) {
        super(activity);
        this.context = context;
        //this.content = content;
        this.activity = activity;
        initComponent();
    }


    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.editor_audio, this);

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            sdPath = Environment.getExternalStorageDirectory();
            sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
            // создаем каталог
            sdPath.mkdirs();
        }


        playButton = (Button) findViewById(R.id.editor_audio_play_b);
        playButton.setOnClickListener(new OnClickListener() {
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
        recStopButton = (Button) findViewById(R.id.editor_audio_record_stop_b);
        recStopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                recordStop();
            }
        });

        seekBar = (SeekBar) findViewById(R.id.editor_audio_seekbar);

        handler = new Handler();


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

    public void playStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void recordStart() {
        try {
            releaseRecorder();

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


    public void playStart() {
        try {
            releasePlayer();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(content);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recordStop() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
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


    MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void start() {
        mediaPlayer = MediaPlayer.create(context, R.raw.voice37);
        seekBar.setMax(mediaPlayer.getDuration());

        mediaPlayer.start();


        /*mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(DATA_SD);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.prepare();
        mediaPlayer.start();*/
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void releaseMediaPlayer() {
        mediaPlayer.release();
        mediaPlayer = null;
    }


    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean deleteEditor() {
        // TODO: 011 11 Jan 18 free up audio player/recorder
        return true;
    }

    @Override
    public int getEditorType() {
        return 0;
    }

    @Override
    public int getEditorRole() {
        return 0;
    }

    @Override
    public Note getNote() {
        return null;
    }

    @Override
    public String getContent() {
        return content;
    }



    /*При удалении пользователем вашего приложения система Android удаляет из внешних хранилищ файлы
    этого приложения, только если они сохраняются в директории из getExternalFilesDir().
*/
}
