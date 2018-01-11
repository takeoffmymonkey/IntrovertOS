package com.example.android.introvert.Editors;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.android.introvert.Notes.Note;

/**
 * Created by takeoff on 011 11 Jan 18.
 */

public class ImageEditor extends RelativeLayout implements MyEditor{
    public ImageEditor(Context context) {
        super(context);
    }

    public ImageEditor (LinearLayout editorContainer, int editorType, int editorRole, boolean exists,
                        Note note, Activity activity){
        super(activity);
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public void deleteEditor() {

    }
}
