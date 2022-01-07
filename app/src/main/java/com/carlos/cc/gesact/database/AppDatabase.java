package com.carlos.cc.gesact.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.carlos.cc.gesact.dao.StudentDao;
import com.carlos.cc.gesact.model.CriterionModel;
import com.carlos.cc.gesact.model.EvidenceModel;
import com.carlos.cc.gesact.model.GroupModel;
import com.carlos.cc.gesact.model.StudentModel;
import com.carlos.cc.gesact.model.SubjectModel;

@Database(entities = {
        StudentModel.class,
        GroupModel.class,
        CriterionModel.class,
        EvidenceModel.class,
        SubjectModel.class}, version = 3)
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

    public abstract StudentDao studentDao();

}
