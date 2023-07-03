package com.example.notesfirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> {

    Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.titletv.setText(note.title);
        holder.contenttv.setText(note.content);
        holder.timestamptv.setText(Utility.timestampToString(note.timestamp));



    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note_item,parent,false);
        return new NoteViewHolder(view);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{
         TextView titletv,contenttv,timestamptv;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            titletv = itemView.findViewById(R.id.tv_note_title);
            contenttv = itemView.findViewById(R.id.tv_note_content);
            timestamptv = itemView.findViewById(R.id.tv_timestamp);

        }
    }
}
