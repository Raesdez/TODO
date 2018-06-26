package com.android.example.todo.ui;

import android.util.Log;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;

public class Task extends RealmObject {

    @PrimaryKey
    private long id;

    private String task;
    //EL status será "PENDIENTE" o "COMPLETADO"
    private String status;
    private Date creates;
    private Date dueTo;
    private Date completed;

    public Task (){

    }

    public Task(long id, String task, String status, Date creates, Date dueTo, Date completed) {
        this.id = id;
        this.task = task;
        this.status = status;
        this.creates = creates;
        this.dueTo = dueTo;
        this.completed = completed;
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

    public Date getCreates() {
        return creates;
    }

    public void setCreates(Date creates) {
        this.creates = creates;
    }

    public Date getDueTo() {
        return dueTo;
    }

    public void setDueTo(Date dueTo) {
        this.dueTo = dueTo;
    }

    public Date getCompleted() {
        return completed;
    }

    public void setCompleted(Date completed) {
        this.completed = completed;
    }

    public boolean createAnUpdateNewTask(Task queryTask){
        try{
            // Get a Realm instance for this thread
            Realm realm = Realm.getDefaultInstance();

            Log.d("id",String.valueOf(queryTask.getId()));
            realm.beginTransaction();
            if(queryTask.getId() == 0){
                long nextID = nextId();
                if(nextID == 0){
                    return false;
                }
                queryTask.setId(nextID);
            }
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

    public List<Task> getSpecificTasks(String queryStatus){
        try{
            // Get a Realm instance for this thread
            Realm realm = Realm.getDefaultInstance();
            RealmResults<Task> queryTask;
            if(queryStatus.matches("COMPLETADO")){
                queryTask = realm.where(Task.class).equalTo("status", queryStatus).findAll().sort("completed", Sort.ASCENDING);
            }else{
                queryTask = realm.where(Task.class).equalTo("status", queryStatus).findAll().sort("dueTo", Sort.ASCENDING);
            }
            if (queryTask.isEmpty())
                return null;
            else
                return queryTask;
        }catch (Exception e){
            return null;
        }
    }

    //Pendiente, si hay dos que se llaman igual???
    public boolean deleteTask(long id){
        try{
            // Get a Realm instance for this thread
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<Task> queryTaskforDelete = realm.where(Task.class).equalTo("id", id).findAll();
            if (!queryTaskforDelete.isEmpty()){
                queryTaskforDelete.get(0).deleteFromRealm();
                realm.commitTransaction();
                return true;
            }else{
                realm.commitTransaction();
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }


    public boolean updateCompletedTask(Task queryTask){
        try{
            // Get a Realm instance for this thread
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            queryTask.setStatus("COMPLETADO");
            queryTask.setCompleted(new java.util.Date());
            Task realmTask = realm.copyToRealmOrUpdate(queryTask);
            realm.commitTransaction();

            return true;
        }catch (Exception e){
            return false;
        }
    }
}
