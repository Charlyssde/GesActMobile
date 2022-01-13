package com.carlos.cc.gesact.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.carlos.cc.gesact.R;
import com.carlos.cc.gesact.model.StudentModel;

public class StudentSelected extends AppCompatActivity {

    private int dataObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_selected);

        dataObject = getIntent().getIntExtra("data", 0);
        Log.e("OBJECT DATA->", String.valueOf(dataObject));

    }
}