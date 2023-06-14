package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Note;
import com.example.myapplication.R;
import com.example.myapplication.fragments.NoteInfo;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    private Context context;
    private List<Note> notes;
    private DBHelper dbHelper;

    public NoteAdapter(Context context, List<Note> notes) {
        super(context, 0, notes);
        this.context = context;
        this.notes = notes;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        }

        Note currentNote = notes.get(position);

        TextView noteTextView = listItemView.findViewById(R.id.note);
        TextView dateTextView = listItemView.findViewById(R.id.date);
        switch (currentNote.getColor()){
            case Color.RED:
                listItemView.findViewById(R.id.view3).setBackgroundResource(R.drawable.rounded_background_red);
                break;
            case Color.BLUE:
                listItemView.findViewById(R.id.view3).setBackgroundResource(R.drawable.rounded_background_blue);
                break;
            case Color.YELLOW:
                listItemView.findViewById(R.id.view3).setBackgroundResource(R.drawable.rounded_background_yellow);
                break;
            case Color.GREEN:
                listItemView.findViewById(R.id.view3).setBackgroundResource(R.drawable.rounded_background_green);
                break;
        }


        String noteText = currentNote.getNoteText();
        int maxLength = 50;
        if (noteText.length() > maxLength) {
            noteText = noteText.substring(0, maxLength - 3) + "...";
        }
        if(noteText.length()==0){
            noteTextView.setText("Немає тексту");
        }
        else {
            noteTextView.setText(noteText);
        }
        dateTextView.setText(currentNote.getDateCreated());
        listItemView.setOnTouchListener(new OnSwipeTouchListener(context) {
            @Override
            public void onSwipeLeft() {
                dbHelper.deleteNoteById(currentNote.getId());
                notes.remove(position);
                notifyDataSetChanged();
            }



            @Override
            public boolean onClick() {
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                NoteInfo noteInfoFragment = new NoteInfo(currentNote);
                fragmentTransaction.replace(R.id.fragment_content, noteInfoFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;
            }
        });
        return listItemView;
    }
}
