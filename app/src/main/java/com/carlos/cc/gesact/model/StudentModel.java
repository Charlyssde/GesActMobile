package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StudentModel {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo (name = "last_name")
    public String lastName;

    @ColumnInfo (name = "surname")
    public String surname;

}
