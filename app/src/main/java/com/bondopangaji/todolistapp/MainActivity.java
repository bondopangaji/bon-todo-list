package com.bondopangaji.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bondopangaji.todolistapp.Adapter.TodoAdapter;
import com.bondopangaji.todolistapp.Model.Todo;
import com.bondopangaji.todolistapp.ViewModel.TodoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loicngou.todolistapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    TodoViewModel todoViewModel;
    final TodoAdapter adapter = new TodoAdapter();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        FloatingActionButton addTodoButton = findViewById(R.id.add_todo_button);
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CreateAndModifyTodoActivity.class);
                startActivityForResult(i,ADD_NOTE_REQUEST);
            }
        });

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                Intent i = new Intent(MainActivity.this, CreateAndModifyTodoActivity.class);
                i.putExtra(CreateAndModifyTodoActivity.EXTRA_TITLE,todo.getTitle());
                i.putExtra(CreateAndModifyTodoActivity.EXTRA_DESCRIPTION,todo.getDescription());
                i.putExtra(CreateAndModifyTodoActivity.EXTRA_CATEGORY,todo.getCategory());
                i.putExtra(CreateAndModifyTodoActivity.EXTRA_ID,todo.getId());
                startActivityForResult(i,EDIT_NOTE_REQUEST);
                Toast.makeText(MainActivity.this, "Edit "+todo.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        todoViewModel.getAllTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                Log.i("TODOS",todos.toString());
                adapter.submitList(todos);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Todo todo = adapter.getTodoAt(position);
                if(direction == ItemTouchHelper.RIGHT){
                    todoViewModel.delete(todo);
                    Toast.makeText(MainActivity.this, "Todo Deleted", Toast.LENGTH_SHORT).show();
                }else{
                    todoViewModel.delete(todo);
                    Toast.makeText(MainActivity.this, "Todo Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ADD_NOTE_REQUEST && resultCode==RESULT_OK) {
            String title = data.getStringExtra(CreateAndModifyTodoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(CreateAndModifyTodoActivity.EXTRA_DESCRIPTION);
            String category = data.getStringExtra(CreateAndModifyTodoActivity.EXTRA_CATEGORY);

            Todo todo = new Todo(title,description,category);
            todoViewModel.insert(todo);
            Toast.makeText(this,"New Todo Inserted",Toast.LENGTH_SHORT).show();

        } else if (requestCode==EDIT_NOTE_REQUEST && resultCode==RESULT_OK) {
            String title = data.getStringExtra(CreateAndModifyTodoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(CreateAndModifyTodoActivity.EXTRA_DESCRIPTION);
            String category = data.getStringExtra(CreateAndModifyTodoActivity.EXTRA_CATEGORY);
            int id = data.getIntExtra(CreateAndModifyTodoActivity.EXTRA_ID,-1);
            Todo todo = new Todo(title,description,category);
            todo.setId(id);
            todoViewModel.update(todo);

            Toast.makeText(this,"Todo Updated",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Todo not saved",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.clear_all_button:
                todoViewModel.deleteAllTodo();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return  super.onOptionsItemSelected(item);
    }
}
