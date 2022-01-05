package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class KindEvaluationModel {

    @PrimaryKey
    public int uid;

    @ColumnInfo (name = "name")
    public String name;

    @ColumnInfo (name = "kind")
    public String kind;

    @ColumnInfo (name = "max_value")
    public float maxValue;

    @ColumnInfo (name = "min_value")
    public float minValue;

}
