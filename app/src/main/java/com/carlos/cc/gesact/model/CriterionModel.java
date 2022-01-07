package com.carlos.cc.gesact.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CriterionModel {

    @PrimaryKey (autoGenerate = true)
    public int criterionId;

    @ColumnInfo (name = "criterion_name")
    public String criterionName;

    @ColumnInfo (name = "criterion_value")
    public float criterionValue;

    @ColumnInfo (name = "criterion_max_value")
    public float criterionMaxValue;

    @ColumnInfo (name = "criterion_min_value")
    public float criterionMinValue;

}
