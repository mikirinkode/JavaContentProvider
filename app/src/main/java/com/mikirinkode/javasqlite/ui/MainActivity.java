package com.mikirinkode.javasqlite.ui;

import static android.provider.BaseColumns._ID;

import static com.mikirinkode.javasqlite.data.db.DatabaseContract.NoteColumns.CONTENT_URI;
import static com.mikirinkode.javasqlite.data.db.DatabaseContract.NoteColumns.DATE;
import static com.mikirinkode.javasqlite.data.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.mikirinkode.javasqlite.data.db.DatabaseContract.NoteColumns.TITLE;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mikirinkode.javasqlite.R;
import com.mikirinkode.javasqlite.data.db.NoteHelper;
import com.mikirinkode.javasqlite.data.entity.Note;
import com.mikirinkode.javasqlite.helper.MappingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    private static final int NOTE_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Notes");


        listView = findViewById(R.id.lv_note);


           FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
        });

        // get data
        LoaderManager.getInstance(this).initLoader(NOTE_LOADER, null, mLoaderCallbacks);
    }

    private final LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {

                @NonNull
                @Override
                public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
                    return new CursorLoader(
                            MainActivity.this,
                            CONTENT_URI,
                            new String[]{_ID, TITLE, DESCRIPTION, DATE},
                            null, null, null
                    );
                }

                @Override
                public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

                    List<Note> noteList = MappingHelper.mapCursorToArrayList(data);

                    if (noteList.size() > 0) {
                        NoteCursorAdapter adapter = new NoteCursorAdapter(MainActivity.this, data, 0);
                        listView.setAdapter(adapter);
                    }
                }

                @Override
                public void onLoaderReset(@NonNull Loader<Cursor> loader) {
                }
            };

    @Override
    public void onResume() {
        super.onResume();
        LoaderManager.getInstance(this).restartLoader(NOTE_LOADER, null, mLoaderCallbacks);
    }
}