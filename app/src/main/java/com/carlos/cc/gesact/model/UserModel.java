package com.carlos.cc.gesact.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserModel {

    @PrimaryKey(autoGenerate = true)
    public int user_id;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;
}
