package com.unveil.temp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;
import com.unveil.temp.fragment.QpaperFragment;

public class PostViwer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "Start";

    private TextView mTextMessage;
    private  TextView name;
    private  TextView email;
    private ImageView personImage;
    String personName;
    Uri personPhoto;
    String personEmail;

    BottomNavigationView navigationView;

    private FirebaseAuth mAuth;
    // [END declare_auth]
    private GoogleSignInClient mGoogleSignInClient;
    Fragment testFragment;

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    String title = "";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item1) {
            Fragment fragmentselected = null;
            switch (item1.getItemId()) {

                case R.id.navigation_QPaper:
                    title = "Question paper";
                    fragmentselected = new QpaperFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentselected).commit();
                    getSupportActionBar().setTitle(title);
                    item1.setCheckable(true);
                    return true;

            }

            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();
            String personId = acct.getId();
            personPhoto = acct.getPhotoUrl();
        }
        Log.e("TAG", String.valueOf(personPhoto));

        navigationView = findViewById(R.id.navigation);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.fragment_container, new QpaperFragment());
        tx.commit();
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (!isConnected()) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_warning)
                    .setTitle("No Internet !")
                    .setCancelable(false)
                    .setMessage("Please turn on data or Wi-Fi to access this app")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();

                        }

                    })
                    .show();
        }

        name = navigationView.getHeaderView(navigationView.getHeaderCount()- 1).findViewById(R.id.AcName);
        name.setText(personName);
        email = navigationView.getHeaderView(navigationView.getHeaderCount()- 1).findViewById(R.id.emailid);
        email.setText(personEmail);
        personImage =navigationView.getHeaderView(navigationView.getHeaderCount()- 1).findViewById(R.id.personImage);
        personImage.setImageURI(personPhoto);
        Picasso.with(this).load(personPhoto).into(personImage);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            Fragment fragmentselected = null;
            fragmentselected = new QpaperFragment();
            title = "SPPU QBank";
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentselected).commit();
            getSupportActionBar().setTitle(title);
            DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer1.closeDrawer(GravityCompat.START);
            navigationView.setVisibility(View.VISIBLE);

            super.onBackPressed();
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragmentselected = null;
        switch (item.getItemId()) {

            case R.id.nav_qpaper:
                fragmentselected = new QpaperFragment();
                //title = "Question paper";
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentselected).commit();
                //getSupportActionBar().setTitle(title);
                //item.setCheckable(true);
                //DrawerLayout drawer =  findViewById(R.id.drawer_layout);
                //drawer.closeDrawer(GravityCompat.START);
                Toast.makeText(PostViwer.this,"Coming Soon ...",Toast.LENGTH_LONG).show();
                //navigationView.setVisibility(View.GONE);

                return true;

            case R.id.nav_home:
                fragmentselected = new QpaperFragment();
                title = "SPPU QBank";
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentselected).commit();
                getSupportActionBar().setTitle(title);
                item.setCheckable(true);
                DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer1.closeDrawer(GravityCompat.START);

                navigationView.setVisibility(View.VISIBLE);
                return true;

            case R.id.nav_sout:
                signOut();
                //Toast.makeText(getApplicationContext(), "Coming Soon...!", Toast.LENGTH_LONG).show();
                return true;



            case R.id.nav_about:
                startActivity(new Intent(this, about.class));
                DrawerLayout drawer2 = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer2.closeDrawer(GravityCompat.START);
                return true;
            // Toast.makeText(getApplicationContext(), "Coming Soon...!", Toast.LENGTH_LONG).show();

            case R.id.nav_share:
                Intent myintent = new Intent(Intent.ACTION_SEND);
                myintent.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=com.unveil.sppuqbank";
                myintent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                myintent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myintent, "Share Using"));
                DrawerLayout drawer3 = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer3.closeDrawer(GravityCompat.START);
                return true;
        }

        return true;
    }

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public boolean pressed() {
        Fragment fragmentselected = null;
        fragmentselected = new QpaperFragment();
        title = "SPPU QBank";
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentselected).commit();
        getSupportActionBar().setTitle(title);
        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer1.closeDrawer(GravityCompat.START);
        navigationView.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        pressed();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {

            //Ask the user if they want to quit
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.appexit_dialog);
            dialog.setCancelable(true);
            Button yes = dialog.findViewById(R.id.yes);
            Button no = dialog.findViewById(R.id.no);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = width;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.show();
            yes.setWidth(width);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostViwer.this.finish();
                    dialog.hide();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.hide();

                }
            });

            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int id = item.getItemId();

        if (id == R.id.about) {
            Intent intent = new Intent(this,about.class);
            this.startActivity(intent);
            return true;
        }
        else if (id == R.id.share)
        {
            Intent myintent = new Intent(Intent.ACTION_SEND);
            myintent.setType("text/plain");
            String shareBody = "https://play.google.com/store/apps/details?id=com.unveil.sppuqbank";
            myintent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
            myintent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(myintent,"Share Using"));
            return true;
        }
        else if (id == R.id.logout)
        {
            signOut();

            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    private boolean isAppInstalled() {
        PackageManager pm = getPackageManager();
        try {

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void signOut() {
        // Firebase sign out
        mAuth.signOut();
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity (new Intent (PostViwer.this, GoogleSignInActivity.class));
                        finish ( );
                    }
                });
    }

}