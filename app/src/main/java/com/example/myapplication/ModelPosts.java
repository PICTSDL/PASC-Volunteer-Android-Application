package com.example.myapplication;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ModelPosts {
    String title, description, date, time, type, ownerOfPost;



    public ModelPosts(String title, String description, String date, String time) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.type = type;
        this.ownerOfPost = ownerOfPost;
    }


    public String getType() {
        return type;
    }

    public String getOwnerOfPost() {

        return ownerOfPost;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public  String getTime() {
        return  time;
    }

}
