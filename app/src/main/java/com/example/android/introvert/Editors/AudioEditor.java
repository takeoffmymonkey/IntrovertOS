package com.example.android.introvert.Editors;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.android.introvert.R;

/**
 * Created by takeoff on 010 10 Jan 18.
 */

public class AudioEditor extends RelativeLayout implements Editable {

    String TAG = "INTROWERT_AUDIO_EDITOR:";

    MediaPlayer mediaPlayer;

    String content;

    private Button playButton;
    private Button stopButton;
    private Button recButton;
    private SeekBar seekBar;

    Context context;

    private int type; // 1 - content, 2 - tags, 3 - comment

    public AudioEditor(Context context, String content) {
        super(context);
        this.context = context;
        this.content = content;
        initComponent();
    }


    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.editor_audio, this);
        mediaPlayer = MediaPlayer.create(context, R.raw.voice37);

        playButton = (Button) findViewById(R.id.editor_audio_play_b);
        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });


        stopButton = (Button) findViewById(R.id.editor_audio_stop_b);
        stopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });

        recButton = (Button) findViewById(R.id.editor_audio_record_b);
        seekBar = (SeekBar) findViewById(R.id.editor_audio_seekbar);


    }

    MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void start() {
        mediaPlayer.start();
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
    public String getContent() {
        return content;
    }


}
