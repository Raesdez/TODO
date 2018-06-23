package com.android.example.todo.ui;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Task extends RealmObject {

    @PrimaryKey
    private long id;

    private String task;
    private String status;
    private Date dateOfCreation;

    public Task (){

    }

    public Task(long id, String task, String status, Date dateOfCreation) {
        this.id = id;
        this.task = task;
        this.status = status;
        this.dateOfCreation = dateOfCreation;
    }

    //RECORDAR.... CUIDADO AL HACER SETTASK FUERA DE UNA TRANSACCION!

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
    
}
