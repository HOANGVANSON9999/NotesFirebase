package com.example.notesfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText titleedt, contentedt;
    ImageButton saveNoteBtn;
    TextView paeTitletv;
    String title,content,docId;
    boolean isEditMode = false;
    TextView deteNotetvbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        titleedt = findViewById(R.id.notes_title_tv);
        contentedt = findViewById(R.id.notes_content_tv);
        saveNoteBtn = findViewById(R.id.save_note_btn);
        paeTitletv = findViewById(R.id.page_title);
        deteNotetvbtn = findViewById(R.id.delete_note_tv);

        //receive data
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        if (docId != null && !docId.isEmpty()){
            isEditMode = true;
        }

        titleedt.setText(title);
        contentedt.setText(content);
        if (isEditMode){
            paeTitletv.setText("Edit your note");
            deteNotetvbtn.setVisibility(View.VISIBLE);
        }



        saveNoteBtn.setOnClickListener((v) -> saveNote());

        deteNotetvbtn.setOnClickListener((v) -> deleteNoteFromFirebase());


    }

    void saveNote(){
        String noteTitle = titleedt.getText().toString();
        String noteContent = contentedt.getText().toString();
        if (noteTitle == null || noteTitle.isEmpty()){
            titleedt.setError("Title is required");
            return;
        }

        Note note = new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());

        saveNoteToFirebase(note);

    }

    void saveNoteToFirebase(Note note){
        DocumentReference documentReference;
        if (isEditMode){
            //updat the note
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);

        }else {
            //creat new note
            documentReference = Utility.getCollectionReferenceForNotes().document();

        }
        documentReference = Utility.getCollectionReferenceForNotes().document();

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //note is added
                    Utility.showToast(NoteDetailsActivity.this, "Note added successfully");
                    finish();

                }else {
                    //failure
                    Utility.showToast(NoteDetailsActivity.this, "Failed while adding note");

                }
            }
        });

    }

    void deleteNoteFromFirebase(){
        DocumentReference documentReference;

            //delete the note
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);
            documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //note is delete
                    Utility.showToast(NoteDetailsActivity.this, "Note deleted successfully");
                    finish();

                }else {
                    //failure
                    Utility.showToast(NoteDetailsActivity.this, "Failed while deleting note");

                }
            }
        });
    }
}