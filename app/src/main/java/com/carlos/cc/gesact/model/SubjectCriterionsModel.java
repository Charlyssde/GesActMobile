package com.carlos.cc.gesact.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

@Entity
public class SubjectCriterionsModel {

    @Embedded
    public SubjectModel subjectModel;

    @Relation(
            parentColumn = "subject_id",
            entityColumn = "criterion_subject_id"
    )
    public List<CriterionModel> criterionModelList;

}
