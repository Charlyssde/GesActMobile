package com.carlos.cc.gesact.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.cc.gesact.R;
import com.carlos.cc.gesact.database.AppDatabase;
import com.carlos.cc.gesact.model.StudentModel;
import com.carlos.cc.gesact.services.Env;
import com.carlos.cc.gesact.services.PreferencesService;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnLogin;
    private EditText userName;
    PreferencesService preferencesService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencesService = PreferencesService.getInstance(MainActivity.this);

        if(preferencesService.getProperty(Env.USER_NAME).equals("")){
            userName = findViewById(R.id.input_user_name);
            btnLogin = findViewById(R.id.btn_login);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!userName.getText().toString().equals("")){
                        preferencesService.setProperty(Env.USER_NAME, userName.getText().toString());
                        goToDashboard();
                    }
                }
            });
        }else{
            goToDashboard();
        }

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

    private void goToDashboard() {
        Toast.makeText(MainActivity.this, "Accediendo", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}