package com.carlos.cc.gesact.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.carlos.cc.gesact.R;
import com.carlos.cc.gesact.database.AppDatabase;
import com.carlos.cc.gesact.model.StudentModel;
import com.carlos.cc.gesact.model.UserModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        register = findViewById(R.id.tv_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                AppDatabase database = AppDatabase.getInstance(MainActivity.this);
//
//                List<StudentModel> lista = database.studentDao().getAll();
//
//                Log.e("SIZE OF LIST", String.valueOf(lista.size()));
//
//                StudentModel student = new StudentModel();
//                student.studentName = "Carlos";
//                student.studentLastName = "Carrillo";
//                student.studentSurname = "Ceballos";
//                student.studentGrade = "1";
//                student.studentGroup = "C";
//
//                database.studentDao().insertAll(student);
//                lista = database.studentDao().getAll();
//                Log.e("SIZE OF LIST AFTER INSERT ", String.valueOf(lista.size()));
//                for (int i = 0; i < lista.size(); i++){
//                    Log.e("Element ->", lista.get(i).toString());
//                }
//            }
//        });


    }
}