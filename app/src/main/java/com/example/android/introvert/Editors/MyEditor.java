package com.example.android.introvert.Editors;

import com.example.android.introvert.Notes.Note;

/**
 * Created by takeoff on 010 10 Jan 18.
 * <p>
 * This is an interface to be used by every editor
 * Placed fields here as cannot use inheritance for editors (they inherit from Relative layout)
 */

public interface MyEditor {
    // Which type of content will be used: 1 - text, 2 - audio, 3 - video, 4 - photo, 5 - image
    int editorType = 0;

    // What is the role (context) in which editor is used: 1 - content, 2 - tags, 3 - comment
    int editorRole = 0;

    // Whether the note exist and its content should be used, or created empty
    boolean exists = false;

    // Note for which editor is created
    Note note = null;

    /* Method for retrieving the content */
    String getContent();

    /* Method for setting content of the editor*/
    void setContent(String content);

    /* Method for releasing/setting editor object to null when not needed */
    void deleteEditor();
}
