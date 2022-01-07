package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EvidenceModel {

    @PrimaryKey(autoGenerate = true)
    public int evidence_id;

    @ColumnInfo (name = "evidence_name")
    public String evidenceName;

    @ColumnInfo (name = "evidence_description")
    public String evidenceDescription;

    @ColumnInfo(name = "evidence_kind")
    public String evidenceKind;

    @Embedded
    public StudentModel studentModel;

    @Embedded
    public SubjectModel subjectModel;
}
