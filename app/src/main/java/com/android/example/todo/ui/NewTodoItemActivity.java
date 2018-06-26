package com.android.example.todo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.android.example.todo.R;

import java.util.Date;

public class NewTodoItemActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo_item);

        Button button = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.editText);
        final CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task newTask = new Task(0,textView.getText() + "","PENDIENTE", new java.util.Date(),
                        new Date(calendarView.getDate()), null);
                newTask.createAnUpdateNewTask(newTask);
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













