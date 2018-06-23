package com.android.example.todo.ui;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Task extends RealmObject {

    @PrimaryKey
    private long id;

    private String task;
    //EL status será "PENDIENTE" o "COMPLETADO"
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


    public boolean createNewTask(Task queryTask){
        try{
            // Get a Realm instance for this thread
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            long nextID = nextId();
            if(nextID == 0){
                return false;
            }
            queryTask.setId(nextID);
            Task realmTask = realm.copyToRealmOrUpdate(queryTask);
            realm.commitTransaction();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public long nextId(){
        try{
            // Get a Realm instance for this thread
            Realm realm = Realm.getDefaultInstance();
            Number num = realm.where(Task.class).max("id");
            int nextID;
            if(num == null) {
                nextID = 1;
            } else {
                nextID = num.intValue() + 1;
            }
            return nextID;
        }catch (Exception e){
            return 0;
        }
    }

    public List<Task> getAllTasks(){
        try{
            // Get a Realm instance for this thread
            Realm realm = Realm.getDefaultInstance();
            RealmResults<Task> queryTask = realm.where(Task.class).findAll();
            if (queryTask.isEmpty())
                return null;
            else
                return queryTask;
        }catch (Exception e){
            return null;
        }
    }

    //Falta ordenarlos
    public List<Task> getSpecificTasks(String queryStatus){
        try{
            // Get a Realm instance for this thread
            Realm realm = Realm.getDefaultInstance();
            RealmResults<Task> queryTask = realm.where(Task.class).equalTo("status", queryStatus).findAll();
            if (queryTask.isEmpty())
                return null;
            else
                return queryTask;
        }catch (Exception e){
            return null;
        }
    }

    //Pendiente, si hay dos que se llaman igual???
    public boolean deleteTask(String queryTask){
        try{
            // Get a Realm instance for this thread
            Realm realm = Realm.getDefaultInstance();
            RealmResults<Task> queryTaskforDelete = realm.where(Task.class).equalTo("task", queryTask).findAll();
            if (!queryTaskforDelete.isEmpty()){
                queryTaskforDelete.get(0).deleteFromRealm();
                return true;
            }else{
              return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    //FALTA EL UPDATE
}
