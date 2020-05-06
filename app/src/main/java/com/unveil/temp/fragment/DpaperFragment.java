package com.unveil.temp.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.unveil.temp.QBranchViewActivity;
import com.unveil.temp.R;
import com.unveil.temp.viewholder.BranchViewHolder;

public  class QpaperFragment extends Fragment {
    RecyclerView mRecycler;
    LinearLayoutManager mManager;
    FirebaseRecyclerAdapter<String, BranchViewHolder> mAdapter;

    SwipeRefreshLayout mSwipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_qpaper, container, false);
        mRecycler = view.findViewById (R.id.recycler_view);
        mRecycler.setHasFixedSize (true);

        mSwipeRefreshLayout = view.findViewById (R.id.swipeRefreshlayout);
        return view;


    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mManager = new LinearLayoutManager(getActivity());
        mManager.setStackFromEnd (true);
        mManager.setReverseLayout(true);
        mRecycler.setLayoutManager (mManager);
        mSwipeRefreshLayout.setRefreshing (true);
        Query postsQuery = getQuery ();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<String>()
                .setQuery(postsQuery, String.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<String, BranchViewHolder>(options) {

            @Override
            public BranchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new BranchViewHolder (inflater.inflate(R.layout.item_branch, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(final BranchViewHolder viewHolder, int position, final String model) {
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
                viewHolder.setText (model);
                viewHolder.setOnClick (new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {

                        final Dialog dialog = new Dialog(getActivity());
                        dialog.setContentView (R.layout.year_qdialog);
                        dialog.setCancelable (true);
                        Button buttonFE = dialog.findViewById (R.id.dialog_btn_fyear);
                        Button buttonSE = dialog.findViewById (R.id.dialog_btn_syear);
                        Button buttonTE = dialog.findViewById (R.id.dialog_btn_tyear);
                        Button buttonBE = dialog.findViewById (R.id.dialog_btn_Byear);

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = width;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                        buttonFE.setWidth (width);
                        buttonFE.setOnClickListener (new View.OnClickListener ( ) {
                            @Override
                            public void onClick(View v) {
                                dialog.hide ();
                                Intent intent =  new Intent(getActivity(), QBranchViewActivity.class);
                                intent.putExtra ("Branch",postKey);
                                intent.putExtra ("Year","FE");
                                intent.putExtra("sem","Sem1");
                                startActivity (intent);

                            }
                        });

                        buttonSE.setOnClickListener (new View.OnClickListener ( ) {
                            @Override
                            public void onClick(View v) {
                                dialog.hide ();
                                Intent intent =  new Intent(getActivity(), QBranchViewActivity.class);
                                intent.putExtra ("Branch",postKey);
                                intent.putExtra ("Year","SE");
                                intent.putExtra("sem","Sem1");
                                startActivity (intent);

                            }

                        });
                        buttonTE.setOnClickListener (new View.OnClickListener ( ) {
                            @Override
                            public void onClick(View v) {
                                dialog.hide ();
                                Intent intent =  new Intent(getActivity(), QBranchViewActivity.class);
                                intent.putExtra ("Branch",postKey);
                                intent.putExtra ("Year","TE");
                                intent.putExtra("sem","Sem1");
                                startActivity (intent);


                            }

                        });
                        buttonBE.setOnClickListener (new View.OnClickListener ( ) {
                            @Override
                            public void onClick(View v) {
                                dialog.hide ();
                                Intent intent =  new Intent(getActivity(), QBranchViewActivity.class);
                                intent.putExtra ("Branch",postKey);
                                intent.putExtra ("Year","BE");
                                intent.putExtra("sem","Sem1");
                                startActivity (intent);

                            }
                        });
                        dialog.show ();
                    }
                });
                // Bind Post to ViewHolder, setting OnClickListener for the star button

            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart ( );
        if(mAdapter!=null){
            mAdapter.startListening ();
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
    }

    @Override
    public void onStop() {
        super.onStop ( );
        if(mAdapter!=null){
            mAdapter.stopListening ();
        }
    }

    private Query getQuery() {
        return FirebaseDatabase.getInstance ().getReference ().child ("Depts");
    }

}
