package com.unveil.temp.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START comment_class]
@IgnoreExtraProperties
public class Comment {

    public String uid;
    public String author;
    public String text;
    public  String photocom;

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Comment(String uid, String author, String text , String photocom) {
        this.uid = uid;
        this.author = author;
        this.text = text;
        this.photocom= photocom;
    }

}
