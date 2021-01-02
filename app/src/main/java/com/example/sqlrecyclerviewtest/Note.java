package com.example.sqlrecyclerviewtest;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey (autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int priority;
    private int dayOfMonth;
    private int month;
    private int year;

    public Note(String title, String description, int priority, int year, int month, int dayOfMonth) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public int getYear(){return year;}

    public int getMonth(){return month;}

    public int getDayOfMonth(){return dayOfMonth;}
}
