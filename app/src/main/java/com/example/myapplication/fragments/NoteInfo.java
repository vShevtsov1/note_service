package com.example.myapplication.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myapplication.DBHelper;
import com.example.myapplication.Note;
import com.example.myapplication.R;


public class NoteInfo extends Fragment {


    private Note note;

    private MultiAutoCompleteTextView multiAutoCompleteTextView;
    private DBHelper dbHelper;



    public NoteInfo(Note note) {
        this.note = note;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_info, container, false);
        multiAutoCompleteTextView = view.findViewById(R.id.textArea);
        dbHelper = new DBHelper(getContext());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        multiAutoCompleteTextView.setText(note.getNoteText());
        multiAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                dbHelper.updateNoteById(note.getId(),newText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}