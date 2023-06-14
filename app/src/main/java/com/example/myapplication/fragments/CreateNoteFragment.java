package com.example.myapplication.fragments;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import com.example.myapplication.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;


public class CreateNoteFragment extends Fragment {

    private MultiAutoCompleteTextView multiAutoCompleteTextView;
    private DBHelper dbHelper;
    private int id;



    public CreateNoteFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_note, container, false);
        multiAutoCompleteTextView = view.findViewById(R.id.textArea);
        dbHelper = new DBHelper(getContext());

        Random random = new Random();
        int colorIndex = random.nextInt(4);
        int[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
        int randomColor = colors[colorIndex];

        id = (int) dbHelper.insertNote("", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")), randomColor);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        multiAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                dbHelper.updateNoteById(id,newText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}