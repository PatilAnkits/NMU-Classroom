package com.unveil.temp.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.unveil.temp.PostShow;
import com.unveil.temp.PostViwer;
import com.unveil.temp.models.QuestionPaperPDF;

import com.unveil.temp.R;
import com.unveil.temp.viewholder.SubjectViewHolder;


public abstract class QsubjectsFragment extends Fragment {

    private static final String TAG = "SubjectFragment";
    private FirebaseRecyclerAdapter<QuestionPaperPDF,SubjectViewHolder> mAdapter;
    // [START define_database_reference]

    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private DatabaseReference mDatabase;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView (inflater, container, savedInstanceState);
        View rootView = inflater.inflate (R.layout.activity_qsubjectfragment, container, false);

        mRecycler = rootView.findViewById (R.id.recycler_view);
        mRecycler.setHasFixedSize (true);



        mSwipeRefreshLayout = rootView.findViewById (R.id.swipeRefreshlayout);
        mDatabase = FirebaseDatabase.getInstance ().getReference ().child ("Paper");

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated (savedInstanceState);

        mManager = new LinearLayoutManager(getActivity ());
        mManager.setReverseLayout (true);
        mManager.setStackFromEnd (true);
        mRecycler.setLayoutManager (mManager);

        mSwipeRefreshLayout.setRefreshing (true);
        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery ();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<QuestionPaperPDF>()
                .setQuery(postsQuery, QuestionPaperPDF.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<QuestionPaperPDF, SubjectViewHolder>(options) {

            @Override
            public SubjectViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new SubjectViewHolder (inflater.inflate(R.layout.item_subject, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(final SubjectViewHolder viewHolder, int position, final QuestionPaperPDF model) {
                final DatabaseReference postRef = getRef(position);
                postRef.addListenerForSingleValueEvent (new ValueEventListener( ) {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mSwipeRefreshLayout.setRefreshing (false);
                        mSwipeRefreshLayout.setEnabled (false);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        mSwipeRefreshLayout.setRefreshing (false);

                    }
                });
                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                        viewHolder.setText (model.name);
                        viewHolder.setOnClick (new View.OnClickListener ( ) {
                            @Override
                            public void onClick(View v) {

                                        Intent intent =  new Intent(getActivity(), PostShow.class);
                                        intent.putExtra ("Branch",getBranch());
                                        intent.putExtra("year",getYear());
                                        intent.putExtra("subject",model.name);
                                        intent.putExtra("sem",getSem());
                                        startActivity (intent);

                            }
                        });
                    }

                // Bind Post to ViewHolder, setting OnClickListener for the star button
        };
        mRecycler.setAdapter(mAdapter);
    }

    protected abstract String getBranch();

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    public abstract Query getQuery();

    public abstract String getSem();
    public abstract String getYear();
}
