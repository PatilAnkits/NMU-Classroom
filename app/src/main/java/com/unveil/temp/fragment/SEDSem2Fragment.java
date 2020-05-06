package com.unveil.temp.fragment;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class SEQSem2Fragment extends QsubjectsFragment {

    private String branch;
    private String year;

    @Override
    public Query getQuery() {
        Query query = FirebaseDatabase.getInstance ().getReference ().child ("Paper").child (branch).child(year).child("Sem2");
        return query;
    }

    public void setData(String year, String branch){
        this.year = year;
        this.branch = branch;
    }

    @Override
    protected String getBranch() {
        return branch;
    }

    @Override
    public String getSem() {
        return "Sem2";
    }
    @Override
    public String getYear() {
        return year;
    }

}
