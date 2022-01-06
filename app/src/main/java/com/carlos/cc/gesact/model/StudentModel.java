package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
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

    @ColumnInfo (name = "student_group")
    public String studentGroup;

    @ColumnInfo (name = "student_grade")
    public String studentGrade;

    @Override
    public String toString() {
        return "StudentModel{" +
                "student_id=" + student_id +
                ", studentName='" + studentName + '\'' +
                ", studentLastName='" + studentLastName + '\'' +
                ", studentSurname='" + studentSurname + '\'' +
                ", studentGroup='" + studentGroup + '\'' +
                ", studentGrade='" + studentGrade + '\'' +
                '}';
    }
}
