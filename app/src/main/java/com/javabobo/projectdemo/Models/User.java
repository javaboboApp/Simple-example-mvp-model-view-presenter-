package com.javabobo.projectdemo.Models;

import android.graphics.Bitmap;

/**
 * Created by luis on 15/02/2018.
 */

public class User {
    private String nameUser;
    private String pathDir;
    private String email;
    private String pass;
    private Bitmap imagenAux;
    private int idUser;

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Bitmap getImagenAux() {
        return imagenAux;
    }

    public void setImagenAux(Bitmap imagenAux) {
        this.imagenAux = imagenAux;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setUserName(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPathDir() {
        return pathDir;
    }

    public void setPathDir(String pathDir) {
        this.pathDir = pathDir;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
