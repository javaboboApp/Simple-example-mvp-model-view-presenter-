package com.javabobo.projectdemo.Models;

import java.io.Serializable;

/**
 * Created by luis on 15/02/2018.
 */

public class GaleryImage implements Serializable{
    private String nameImage;

    public String getImageName() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }
}
