package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SubjectModel {

    @PrimaryKey(autoGenerate = true)
    public int subject_id;

    @ColumnInfo(name = "subject_name")
    public String subjectName;

    @ColumnInfo (name = "subject_kind")
    public String subjectKind;

}
