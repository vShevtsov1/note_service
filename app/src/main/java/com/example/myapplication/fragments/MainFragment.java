    package com.example.myapplication.fragments;

    import android.graphics.Color;
    import android.os.Bundle;
    import android.view.GestureDetector;
    import android.view.LayoutInflater;
    import android.view.MotionEvent;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.ListView;
    import android.widget.SearchView;

    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentTransaction;

    import com.example.myapplication.DBHelper;
    import com.example.myapplication.Note;
    import com.example.myapplication.NoteAdapter;
    import com.example.myapplication.R;
    import com.google.android.material.floatingactionbutton.FloatingActionButton;

    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.Date;
    import java.util.List;

    public class MainFragment extends Fragment {

        private ListView noteListView;
        private NoteAdapter noteAdapter;
        private DBHelper dbHelper;
        private void performSearch(String query) {
            List<Note> filteredNotes = dbHelper.findNotesByPartText(query);
            noteAdapter.clear();
            noteAdapter.addAll(filteredNotes);
            noteAdapter.notifyDataSetChanged();
        }
        public MainFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main, container, false);
            dbHelper = new DBHelper(getContext());
            List<Note> allNotes = dbHelper.getAllNotes();
            noteListView = view.findViewById(R.id.noteItems);
            noteAdapter = new NoteAdapter(getContext(), allNotes);
            noteListView.setAdapter(noteAdapter);

            SearchView searchView = view.findViewById(R.id.searchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Perform search or filter based on the submitted query
                    performSearch(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Perform search or filter as the text changes
                    performSearch(newText);
                    return true;
                }
            });

            FloatingActionButton fab = view.findViewById(R.id.floatingActionButton2);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment newFragment = new CreateNoteFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_content, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
            return view;
        }



    }
