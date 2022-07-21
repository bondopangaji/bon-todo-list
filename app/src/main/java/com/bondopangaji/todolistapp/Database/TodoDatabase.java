package com.bondopangaji.todolistapp.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bondopangaji.todolistapp.Model.Todo;
import com.bondopangaji.todolistapp.Dao.TodoDao;

@Database(entities = {Todo.class},version = 1, exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase instance = null;
    public abstract TodoDao todoDao();

    public static synchronized TodoDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),TodoDatabase.class,"todo_app_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallBack = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private TodoDao todoDao;

        PopulateDbAsyncTask(TodoDatabase todoDatabase){
            this.todoDao = todoDatabase.todoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.insert(new Todo("Title 1", "Description 1", "Category 1"));
            todoDao.insert(new Todo("Title 2", "Description 2", "Category 2"));
            todoDao.insert(new Todo("Title 3", "Description 3", "Category 3"));
            todoDao.insert(new Todo("Title 4", "Description 4", "Category 4"));
            todoDao.insert(new Todo("Title 5", "Description 5", "Category 5"));
            todoDao.insert(new Todo("Title 6", "Description 6", "Category 6"));
            return null;
        }
    }
}
