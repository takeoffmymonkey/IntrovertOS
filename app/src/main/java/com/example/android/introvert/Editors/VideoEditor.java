package com.example.android.introvert.Editors;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.android.introvert.Notes.Note;

/**
 * Created by takeoff on 011 11 Jan 18.
 */

public class VideoEditor extends RelativeLayout implements MyEditor {

    private final String TAG = "INTROWERT_VIDEO_EDITOR";

    public VideoEditor(Context context) {
        super(context);
    }

    public VideoEditor(LinearLayout editorContainer, int editorType, int editorRole, boolean exists,
                       Note note, Activity activity) {
        super(activity);
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
        return null;
    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public boolean deleteEditor() {
        // TODO: 011 11 Jan 18 free up video player/recorder 
        return true;
    }
}
