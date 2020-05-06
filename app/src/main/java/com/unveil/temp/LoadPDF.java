package com.unveil.temp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoadPDF extends AppCompatActivity {
    private static final String TAG = "LoadPDF";
    WebView pdfView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pdf);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
         pdfView = findViewById(R.id.pdf);
         progressBar = findViewById(R.id.prob);
        Snackbar.make (this.findViewById (android.R.id.content),"Wait Page is under Loading  !!!", Snackbar.LENGTH_LONG).show ();
        progressBar.setVisibility(View.VISIBLE);
                 String url = getIntent().getStringExtra("URl");
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            finish();
        }

        //pdfView.getSettings().setJavaScriptEnabled(true);
        //pdfView.loadUrl("https://firebasestorage.googleapis.com/v0/b/temp-7f552.appspot.com/o/Uploads%2F1585317794879?alt=media&token=3bcae59c-26b0-4814-b347-70759603be77\n");

        }



}

