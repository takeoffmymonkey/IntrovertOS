package com.example.android.introvert.Editors;

import com.example.android.introvert.Notes.Note;

/**
 * Created by takeoff on 010 10 Jan 18.
 * <p>
 * This is an interface to be used by every editor
 */

public interface MyEditor {

    /* Retrieving editor type (1 -text, 2 -audio, etc.) */
    int getEditorType();

    /* Retrieving editor role (1 - content, 2 - tags, 3 - comment) */
    int getEditorRole();

    /* Retrieving link to current note in use */
    Note getNote();

    /* Retrieving the content */
    String getContent();

    /* Setting content of the editor*/
    void setContent(String content);

    /* Releasing/setting editor object to null when not needed.
     * True if editor should free up its resources itself */
    boolean deleteEditor();
}
