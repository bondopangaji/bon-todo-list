package com.bondopangaji.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.loicngou.todolistapp.R;

public class CreateAndModifyTodoActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "com.bondopangaji.todolistapp.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.bondopangaji.todolistapp.EXTRA_DESCRIPTION";
    public static final String EXTRA_CATEGORY =
            "com.bondopangaji.todolistapp.EXTRA_CATEGORY";
    public static final String EXTRA_ID =
            "com.bondopangaji.todolistapp.EXTRA_DATE";

    private TextInputEditText titleEditText;
    private TextInputEditText descriptionEditText;
    private TextInputEditText categoryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);

        titleEditText = findViewById(R.id.title_edit_text);
        descriptionEditText = findViewById(R.id.descriptiion_edit_text);
        categoryEditText = findViewById(R.id.category_edit_text);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent i = getIntent();
        if(i.hasExtra(EXTRA_ID)){
            setTitle("Edit Todo");
            titleEditText.setText(i.getStringExtra(CreateAndModifyTodoActivity.EXTRA_TITLE));
            descriptionEditText.setText(i.getStringExtra(CreateAndModifyTodoActivity.EXTRA_DESCRIPTION));
            categoryEditText.setText(i.getStringExtra(CreateAndModifyTodoActivity.EXTRA_CATEGORY));
        } else {
            setTitle("Add Todo");
        }
    }

    public void saveTodo(){
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String category = categoryEditText.getText().toString();

        if(title.trim().isEmpty() || description.trim().isEmpty() || category.trim().isEmpty()){
            Toast.makeText(this,"Please Fill all Field",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_CATEGORY,category);

        if(getIntent().hasExtra(EXTRA_ID)) {
            int id = getIntent().getIntExtra(CreateAndModifyTodoActivity.EXTRA_ID,-1);
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_todo_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_todo :
                saveTodo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
