package com.example.notesfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText titleedt, contentedt;
    ImageButton saveNoteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        titleedt = findViewById(R.id.notes_title_tv);
        contentedt = findViewById(R.id.notes_content_tv);
        saveNoteBtn = findViewById(R.id.save_note_btn);

        saveNoteBtn.setOnClickListener((v) -> saveNote());
    }

    void saveNote(){
        String noteTitle = titleedt.getText().toString();
        String noteContent = contentedt.getText().toString();
        if (noteTitle == null || noteTitle.isEmpty()){
            titleedt.setError("Title is required");
            return;
        }



    }
}