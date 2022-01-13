package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class SubjectModel {

    public SubjectModel() {}

    public SubjectModel(String subjectName, String subjectKind, com.carlos.cc.gesact.model.GroupModel groupModel) {
        this.subjectName = subjectName;
        this.subjectKind = subjectKind;
        GroupModel = groupModel;
    }

    @PrimaryKey(autoGenerate = true)
    public int subject_id;

    @ColumnInfo(name = "subject_name")
    public String subjectName;

    @ColumnInfo (name = "subject_kind")
    public String subjectKind;

    @Embedded (prefix = "subject")
    public GroupModel GroupModel;

}
