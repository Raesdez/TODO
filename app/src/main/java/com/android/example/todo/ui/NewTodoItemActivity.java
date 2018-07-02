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
                /**
                 * En este método se define la accion que se ejecura cuando el usuario presione el boton de
                 * guardar una nueva actividad. Se castea un objeto de tipo Task al cual se le pasan como
                 * parametros en el constructor todos los atributos necesarios para construir la tarea que
                 * el usuario esta creando y por ultimo ejecuta el metodo createAnUpdateNewTask.
                 */
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













