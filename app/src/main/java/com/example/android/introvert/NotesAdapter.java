package com.example.android.introvert;

/**
 * Created by takeoff on 004 04 Jan 18.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        CardView noteItemCV;
        TextView noteItemNameTV;
        TextView noteItemContentTV;
        ImageView noteItemIconIV;

        NoteViewHolder(View itemView) {
            super(itemView);
            noteItemCV = (CardView) itemView.findViewById(R.id.note_item_cv);
            noteItemNameTV = (TextView) itemView.findViewById(R.id.note_item_name_tv);
            noteItemContentTV = (TextView) itemView.findViewById(R.id.note_item_content_tv);
            noteItemIconIV = (ImageView) itemView.findViewById(R.id.note_item_icon_iv);
        }
    }

    List<Person> persons;

    public NotesAdapter(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_item, viewGroup, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder noteViewHolder, int i) {


        noteViewHolder.noteItemNameTV.setText(persons.get(i).name);
        noteViewHolder.noteItemContentTV.setText(persons.get(i).age);
        noteViewHolder.noteItemIconIV.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}
