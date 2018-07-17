package com.javabobo.projectdemo.Models;

import java.io.Serializable;

/**
 * Created by luis on 16/02/2018.
 */

public class Task implements Serializable {
    private int id;
    private int idUser;
    private String title;
    //0 is not finished, 1 finished
    private int isFinish;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int isFinish() {
        return isFinish;
    }

    public void setFinish(int finish) {
        isFinish = finish;
    }
}
