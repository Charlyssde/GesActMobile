package com.carlos.cc.gesact.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.carlos.cc.gesact.model.UserModel;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM usermodel")
    List<UserModel> getAll();

    @Query("SELECT * FROM usermodel WHERE user_id IN (:userIds)")
    List<UserModel> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM usermodel WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    UserModel findByName(String first, String last);

    @Query("SELECT * FROM usermodel WHERE username = :username AND password = :password LIMIT 1")
    UserModel login(String username, String password);

    @Insert
    void insertAll(UserModel... users);

    @Delete
    void delete(UserModel user);

}
