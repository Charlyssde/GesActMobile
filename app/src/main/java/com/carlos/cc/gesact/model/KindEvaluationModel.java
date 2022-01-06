package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class KindEvaluationModel {

    @PrimaryKey(autoGenerate = true)
    public int kind_evaluation_id;

    @ColumnInfo (name = "evaluation_name")
    public String evaluationName;

    @ColumnInfo (name = "evaluation_kind")
    public String evaluationKind;

    @ColumnInfo (name = "evaluation_max_value")
    public float evaluationMaxValue;

    @ColumnInfo (name = "evaluation_min_value")
    public float evaluationMinValue;

}
