package com.example.android.introvert.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.introvert.R;

import static com.example.android.introvert.Database.DbHelper.ID_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTES_CONTENT_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTES_NAME_COLUMN;

/**
 * Created by takeoff on 005 05 Jan 18.
 */

public class NotesCursorAdapter extends CursorAdapter {

    public NotesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        //Get current row ID
        final int rowIdInt = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COLUMN));

        ImageView itemImage = (ImageView) view.findViewById(R.id.item_note_icon);

        //Set item name
        final String itemName = cursor.getString(cursor.getColumnIndexOrThrow(NOTES_NAME_COLUMN));
        final TextView itemNameTextView = (TextView) view.findViewById(R.id.item_note_name);
        itemNameTextView.setText(itemName);


        //Set item content
        final String itemContent = cursor.getString(cursor.getColumnIndexOrThrow(NOTES_CONTENT_COLUMN));
        final TextView itemContentTextView = (TextView) view.findViewById(R.id.item_note_content);
        itemContentTextView.setText(itemContent);


        //Set onclick listener
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "one, one", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
