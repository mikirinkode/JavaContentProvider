package com.mikirinkode.javasqlite.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.mikirinkode.javasqlite.R;
import com.mikirinkode.javasqlite.data.db.DatabaseContract;

public class NoteCursorAdapter extends CursorAdapter {

    public NoteCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvDate = view.findViewById(R.id.tv_item_date);
        TextView tvTitle = view.findViewById(R.id.tv_item_title);
        TextView tvDesc = view.findViewById(R.id.tv_item_description);

        String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE));
        String desc = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION));

        tvDate.setText(date);
        tvTitle.setText(title);
        tvDesc.setText(desc);
    }
}
