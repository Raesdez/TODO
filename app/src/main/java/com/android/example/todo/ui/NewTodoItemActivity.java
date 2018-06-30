package com.android.example.todo.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.android.example.todo.R;

import java.util.Date;

public class NewTodoItemActivity extends AppCompatActivity {
    TextView textView;
    Task savedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo_item);


        if(getIntent() != null){
            EditText editTask = findViewById( R.id.editText);
            long id = 0;
            Bundle passedBundle = getIntent().getExtras();

            id = (long) Long.valueOf(passedBundle.getString("task"));
            savedTask = new Task();
            savedTask = savedTask.findByID(id);
            if (savedTask != null) {
                editTask.setText(savedTask.getTask());
            }
        }

        Button button = (Button)findViewById(R.id.button_add);
        textView = (TextView)findViewById(R.id.editText);
        final CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long newId = 0;
                if(savedTask != null){
                    newId = savedTask.getId();
                }
                Task newTask = new Task(newId,textView.getText() + "","PENDIENTE", new java.util.Date(),
                        new Date(calendarView.getDate()), null);
                newTask.createAnUpdateNewTask(newTask);

                Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


}













