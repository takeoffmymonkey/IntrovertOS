package com.example.android.introvert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by takeoff on 024 24 Oct 17.
 */

public class TimelineFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    //The "Content authority" is a name for the entire content provider
    static final String CONTENT_AUTHORITY = "com.example.android.introvert";

    //base of all URI's which apps will use to contact the content provider.
    private static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);



    String TAG = "INTROWERT_TIMELINE:";

    private MainActivity main;
    private SQLiteDatabase db;
    private SimpleCursorAdapter simpleCursorAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "IN ONATTACH");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "IN ONCREATE");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "IN ONCREATEVIEW");
        main = (MainActivity) getActivity();
        db = main.db;


        View timelineView = inflater.inflate(R.layout.tab_timeline, container, false);

        Button b1 = (Button) timelineView.findViewById(R.id.button);
        Button b2 = (Button) timelineView.findViewById(R.id.button2);

        final TextView t1 = (TextView) timelineView.findViewById(R.id.textView1);
        final TextView t2 = (TextView) timelineView.findViewById(R.id.textView2);


        b1.setText("Put in db");
        b2.setText("Remove from db");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putInDb();
                int[] values = checkDb();

                Toast.makeText(main, Integer.toString(values[0]), Toast.LENGTH_SHORT).show();
                Toast.makeText(main, Integer.toString(values[1]), Toast.LENGTH_SHORT).show();
                /*t1.setText(values[0]);
                t2.setText(values[1]);
*/
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putOutDb();
                int[] values = checkDb();


                Toast.makeText(main, Integer.toString(values[0]), Toast.LENGTH_SHORT).show();
                Toast.makeText(main, Integer.toString(values[1]), Toast.LENGTH_SHORT).show();

                /*t1.setText(values[0]);
                t2.setText(values[1]);
            */
            }
        });

        simpleCursorAdapter = new SimpleCursorAdapter(getContext(), R.layout.tab_timeline, null,
                new String[]{IntrovertDbHelper.SETTINGS_1_COLUMN, IntrovertDbHelper.SETTINGS_2_COLUMN},
                new int[]{R.id.textView1, R.id.textView2,}, 0);


        //Return inflated layout for this fragment
        return timelineView;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
        Log.i(TAG, "IN ONACTIVITYCREATED");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "IN ONSTART");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "IN ONRESUME");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "IN ONPAUSE");
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "IN ONSTOP");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "IN ONDESTROYVIEW");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "IN ONDESTROY");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "IN ONDETACH");
    }


    void putInDb() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IntrovertDbHelper.SETTINGS_1_COLUMN, 2);
        contentValues.put(IntrovertDbHelper.SETTINGS_2_COLUMN, 4);

        if (db.insert(IntrovertDbHelper.SETTINGS_TABLE_NAME, null, contentValues) == -1) {
            Log.e(TAG, "ERROR INSERTING");
        }

    }

    void putOutDb() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IntrovertDbHelper.SETTINGS_1_COLUMN, 0);
        contentValues.put(IntrovertDbHelper.SETTINGS_2_COLUMN, 0);

        if (db.update(IntrovertDbHelper.SETTINGS_TABLE_NAME, contentValues,
                IntrovertDbHelper.ID_COLUMN + "=?", new String[]{"1"}) == -1) {
            Log.e(TAG, "ERROR DELETIGN");
        }
    }

    int[] checkDb() {
        int[] values = {-1, -1};

        Cursor cursor = db.query(IntrovertDbHelper.SETTINGS_TABLE_NAME, null,
                null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            values[0] = cursor.getInt(cursor.getColumnIndex(IntrovertDbHelper.SETTINGS_1_COLUMN));
            values[1] = cursor.getInt(cursor.getColumnIndex(IntrovertDbHelper.SETTINGS_2_COLUMN));
        } else {
            Log.e(TAG, "ERROR QUERING");
        }

        cursor.close();

        return values;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                Uri.withAppendedPath(BASE_CONTENT_URI, IntrovertDbHelper.SETTINGS_TABLE_NAME)
                , null, null, null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        simpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        simpleCursorAdapter.swapCursor(null);
    }
}
