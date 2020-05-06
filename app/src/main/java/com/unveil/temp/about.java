package com.unveil.temp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_about);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//show back button

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
