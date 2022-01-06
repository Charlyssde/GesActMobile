package com.carlos.cc.gesact.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.carlos.cc.gesact.R;
import com.google.android.material.button.MaterialButton;

public class RegisterActivity extends AppCompatActivity {

    private MaterialButton register, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();


    }
}