package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ActivityModel {

    @PrimaryKey
    public int uid;

    @ColumnInfo (name = "name")
    public String name;

    @ColumnInfo (name = "description")
    public String description;

    @Embedded public KindEvaluationModel kindEvaluationModel;

    @Embedded public SubjectModel subjectModel;
}
