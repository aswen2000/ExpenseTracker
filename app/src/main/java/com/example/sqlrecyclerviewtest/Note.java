package com.example.sqlrecyclerviewtest;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey (autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private double amount;
//    private int priority;
    private int dayOfMonth;
    private int month;
    private int year;

    public Note(String title, String description, double amount /*int priority*/, int year, int month, int dayOfMonth) {
        this.title = title;
        this.description = description;
//        this.priority = priority;
        this.amount = amount;
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

    public double getAmount(){
        return amount;
    }

//    public int getPriority() {
//        return priority;
//    }

    public int getYear(){return year;}

    public int getMonth(){return month;}

    public int getDayOfMonth(){return dayOfMonth;}
}
