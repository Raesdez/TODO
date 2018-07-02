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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * Esta es una actividad que permite agregar una nueva tarea a la lista del usuario.
 * La vista consta de varios inputs de  texto, en donde el usuario puede agregar las tareas pendientes que desee.
 * Tambien incluye un calendario, en el que se puede seleccionar la fecha de culminacion de la tarea y por ultimo
 * un boton, que al ser pulsado interactua con la clase Task, la cual permite guardar en la base de datos
 * de realm la tarea recien creada, para que cuando se cierre esta actividad vuelva a las lista de cosas por hacer
 * e incluirá esta nueva tarea.
 */

public class NewTodoItemActivity extends AppCompatActivity {
    TextView textView;
    Task savedTask;
    Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo_item);
        selectedDate = new Date();
        this.setTitle(getString(R.string.create_task_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if(getIntent() != null){
            EditText editTask = findViewById( R.id.editText);
            long id = 0;
            Bundle passedBundle = getIntent().getExtras();
            if(passedBundle != null) {
                id = (long) Long.valueOf(passedBundle.getString("task"));
                savedTask = new Task();
                savedTask = savedTask.findByID(id);
                this.setTitle(getString(R.string.edit_task_title));
                if (savedTask != null) {
                    editTask.setText(savedTask.getTask());
                }
            }
        }

        Button button = (Button)findViewById(R.id.button_add);
        textView = (TextView)findViewById(R.id.editText);
        final CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {

            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                selectedDate = new Date();
                try {
                    selectedDate = formatter.parse(dayOfMonth+"/"+month+"/"+year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }//met
        });
/**
 * En este método se define la accion que se ejecura cuando el usuario presione el boton de
 * guardar una nueva actividad. Se castea un objeto de tipo Task al cual se le pasan como
 * parametros en el constructor todos los atributos necesarios para construir la tarea que
 * el usuario esta creando y por ultimo ejecuta el metodo createAnUpdateNewTask.
 */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long newId = 0;
                if(savedTask != null){
                    newId = savedTask.getId();
                }
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                //dateFormat.format(calendarView);

                Task newTask = new Task(newId,textView.getText() + "","PENDIENTE", new java.util.Date(),
                        selectedDate, null);
                newTask.createAnUpdateNewTask(newTask);

                Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });


    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }*/


}













