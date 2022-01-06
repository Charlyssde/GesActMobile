package com.carlos.cc.gesact.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.carlos.cc.gesact.dao.StudentDao;
import com.carlos.cc.gesact.dao.UserDao;
import com.carlos.cc.gesact.model.ActivityModel;
import com.carlos.cc.gesact.model.DeliveredActivityModel;
import com.carlos.cc.gesact.model.KindEvaluationModel;
import com.carlos.cc.gesact.model.StudentModel;
import com.carlos.cc.gesact.model.SubjectModel;
import com.carlos.cc.gesact.model.UserModel;

@Database(entities = {
        UserModel.class,
        StudentModel.class,
        ActivityModel.class,
        DeliveredActivityModel.class,
        SubjectModel.class,
        KindEvaluationModel.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "GesAct.db";
    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }

    protected AppDatabase(){}

    private static AppDatabase create(Context context){
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME).fallbackToDestructiveMigration().build();
    }

    public abstract UserDao userDao();
    public abstract StudentDao studentDao();

}
