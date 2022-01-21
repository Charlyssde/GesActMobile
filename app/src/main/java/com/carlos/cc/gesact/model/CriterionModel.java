package com.carlos.cc.gesact.model;


import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CriterionModel {

    public CriterionModel(){}

    public CriterionModel(String criterionName, float criterionValue, String criterionKind, float criterionMaxValue, float criterionMinValue, SubjectModel subjectModel) {
        this.criterionName = criterionName;
        this.criterionValue = criterionValue;
        this.criterionKind = criterionKind;
        this.criterionMaxValue = criterionMaxValue;
        this.criterionMinValue = criterionMinValue;
        this.subjectModel = subjectModel;
    }

    private static final String nul = null;

    @PrimaryKey (autoGenerate = true)
    public int criterionId;

    @ColumnInfo (name = "criterion_name")
    public String criterionName;

    @ColumnInfo (name = "criterion_value")
    public float criterionValue;

    @ColumnInfo(name = "criterion_kind")
    public String criterionKind;

    @ColumnInfo (name = "criterion_max_value")
    public float criterionMaxValue;

    @ColumnInfo (name = "criterion_min_value")
    public float criterionMinValue;

    @Embedded (prefix = "criterion")
    public SubjectModel subjectModel;

}
