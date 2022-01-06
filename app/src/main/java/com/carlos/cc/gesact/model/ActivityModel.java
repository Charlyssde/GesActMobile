package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ActivityModel {

    @PrimaryKey(autoGenerate = true)
    public int activity_id;

    @ColumnInfo (name = "activity_name")
    public String activityName;

    @ColumnInfo (name = "activity_description")
    public String activityDescription;

    @Embedded public KindEvaluationModel kindEvaluationModel;

    @Embedded public SubjectModel subjectModel;
}
