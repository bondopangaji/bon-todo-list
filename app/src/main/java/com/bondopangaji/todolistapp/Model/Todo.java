package com.bondopangaji.todolistapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todos_table")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    int id;

    private String title;
    private String description;
    private String category;

    public Todo(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
