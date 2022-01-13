package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class StudentModel{


    public static final List<StudentModel> ITEMS = new ArrayList<StudentModel>();

    private static void addItem(StudentModel item) {
        ITEMS.add(item);
    }

    private static StudentModel createItem(int i) {
        return new StudentModel("Carlos " + i, "Carrillo");
    }

    static {
        // Add some sample items.
        for (int i = 1; i <= 25; i++) {
            addItem(createItem(i));
        }
    }

    public StudentModel() {}

    public StudentModel(String studentName, String studentLastName) {
        this.studentName = studentName;
        this.studentLastName = studentLastName;
    }

    public StudentModel(String studentName, String studentLastName, String studentSurname, String studentBirthday, String studentContact, com.carlos.cc.gesact.model.GroupModel groupModel) {
        this.studentName = studentName;
        this.studentLastName = studentLastName;
        this.studentSurname = studentSurname;
        this.studentBirthday = studentBirthday;
        this.studentContact = studentContact;
        GroupModel = groupModel;
    }

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
