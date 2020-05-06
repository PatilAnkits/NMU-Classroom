package com.unveil.temp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.unveil.temp.fragment.MyPostsFragment;
import com.unveil.temp.fragment.QpaperFragment;
import com.unveil.temp.fragment.RecentPostsFragment;
import com.unveil.temp.fragment.SEQSem1Fragment;
import com.unveil.temp.fragment.SEQSem2Fragment;

import java.time.Year;


public class PostShow extends BaseActivity {

    private static final String TAG = "PostViwer";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_show);

        final String subject = getIntent().getStringExtra("subject");
        final String  sem = getIntent().getStringExtra("sem");
        final String  branch =getIntent().getStringExtra("Branch");
        final String year = getIntent().getStringExtra("year");

        Log.e("NULL","'year' is "+year);
        Log.e("NULL","'sem' is "+sem);
        Log.e("NULL","'branch' is "+branch);
        Log.e("NULL","'subject' is "+subject);

        final MyPostsFragment MyPost = new MyPostsFragment ();
        final RecentPostsFragment RecentPost = new RecentPostsFragment ();

        MyPost.setData (year,branch,sem,subject);
        RecentPost.setData (year,branch,sem,subject);

        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            private final Fragment[] mFragments = new Fragment[] {


                    RecentPost,
                    MyPost,

            };
            private final String[] mFragmentNames = new String[] {


                    "Recent Posts",
                    getString(R.string.heading_my_posts),

            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Button launches NewPostActivity
        findViewById(R.id.fabNewPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(PostShow.this,NewPostActivity.class );
                intent.putExtra ("Branch",branch);
                intent.putExtra("year", year);
                intent.putExtra("subject",subject);
                intent.putExtra("sem",sem);
                startActivity(intent);
            }
        });
    }


}