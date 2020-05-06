package com.unveil.temp.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyPostsFragment extends PostListFragment {
    private  String branch;
    private  String year;
    private  String sem;
    private String subject;
    public MyPostsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-posts")
                .child(getUid());
    }
    @Override
    public String getSem() {
        return sem;
    }

    public void setData(String year, String branch ,String sem, String subject){
        this.year = year;
        this.branch = branch;
        this.sem = sem;
        this.subject = subject;

    }
    @Override
    public String getBranch() {
        return branch;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getYear() {return year;}

}