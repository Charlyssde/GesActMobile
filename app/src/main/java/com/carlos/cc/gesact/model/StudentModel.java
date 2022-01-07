package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StudentModel {

    @PrimaryKey (autoGenerate = true)
    public int student_id;

    @ColumnInfo(name = "student_name")
    public String studentName;

    @ColumnInfo (name = "student_last_name")
    public String studentLastName;

    @ColumnInfo (name = "student_surname")
    public String studentSurname;

    @ColumnInfo (name = "student_birthday")
    public String studentBirthday;

    @ColumnInfo (name = "student_contact")
    public String studentContact;

    @Embedded(prefix = "student")
    public GroupModel GroupModel;

    @Override
    public String toString() {
        return "StudentModel{" +
                "student_id=" + student_id +
                ", studentName='" + studentName + '\'' +
                ", studentLastName='" + studentLastName + '\'' +
                ", studentSurname='" + studentSurname + '\'' +
                '}';
    }
}
