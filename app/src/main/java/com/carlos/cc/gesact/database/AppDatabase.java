package com.carlos.cc.gesact.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.carlos.cc.gesact.dao.UserDao;
import com.carlos.cc.gesact.model.UserModel;

@Database(entities = {UserModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
