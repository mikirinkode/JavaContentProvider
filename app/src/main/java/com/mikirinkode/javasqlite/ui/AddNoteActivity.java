package com.mikirinkode.javasqlite.ui;

import static com.mikirinkode.javasqlite.data.db.DatabaseContract.NoteColumns.CONTENT_URI;
import static com.mikirinkode.javasqlite.data.db.DatabaseContract.NoteColumns.DATE;
import static com.mikirinkode.javasqlite.data.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.mikirinkode.javasqlite.data.db.DatabaseContract.NoteColumns.TITLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mikirinkode.javasqlite.R;
import com.mikirinkode.javasqlite.data.db.NoteHelper;
import com.mikirinkode.javasqlite.data.entity.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {

    private EditText edtTitle, edtDescription;
    private NoteHelper noteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        edtTitle = findViewById(R.id.edt_title);
        edtDescription = findViewById(R.id.edt_description);
        Button btnAdd = findViewById(R.id.btn_add);

        noteHelper = NoteHelper.getInstance(getApplicationContext());
        noteHelper.open();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Note");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnAdd.setOnClickListener(view -> {
            boolean validInput = true;
            String title = edtTitle.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();

            if (title.isEmpty()){
                edtTitle.setError("Title is empty");
                validInput = false;
            }
            if (description.isEmpty()){
                edtDescription.setError("Description is empty");
                validInput = false;
            }

            if (validInput){
                ContentValues values = new ContentValues();
                values.put(TITLE, title);
                values.put(DESCRIPTION, description);
                values.put(DATE, getCurrentDate());

                // insert data to content provider using content uri as parameter
                getContentResolver().insert(CONTENT_URI, values);

                // back to main activity
                Intent backToHome = new Intent(this, MainActivity.class);
                backToHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(backToHome);
            }
        });
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}