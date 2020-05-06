package com.unveil.temp.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class RecentPostsFragment extends PostListFragment {
    private  String branch;
    private  String year;
    private  String sem;
    private String subject;



    public RecentPostsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("Paper").child(branch).child(year).child(sem).child(subject).child("Posts")
                .limitToFirst(100);
        // [END recent_posts_query]

        return recentPostsQuery;
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
