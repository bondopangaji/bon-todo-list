package com.bondopangaji.todolistapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bondopangaji.todolistapp.Model.Todo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("SELECT * FROM todos_table")
    LiveData<List<Todo>> getAllTodo();

    @Query("DELETE FROM todos_table")
    void deleteAllTodo();
}
