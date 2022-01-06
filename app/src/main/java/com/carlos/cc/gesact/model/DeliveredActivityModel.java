package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class DeliveredActivityModel {

    @PrimaryKey(autoGenerate = true)
    public int delivered_id;

    @Embedded StudentModel studentModel;

    @Embedded ActivityModel activityModel;

    @ColumnInfo (name = "date_delivered")
    public String dateDelivered;

    @ColumnInfo (name = "final_grade")
    public float finalGrade;

}
