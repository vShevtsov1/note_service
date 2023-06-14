package com.example.myapplication;

public class Note {
    private int id;
    private String noteText;
    private String dateCreated;
    private int color;

    public Note(int id, String noteText, String dateCreated, int color) {
        this.id = id;
        this.noteText = noteText;
        this.dateCreated = dateCreated;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getNoteText() {
        return noteText;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public int getColor() {
        return color;
    }
}
