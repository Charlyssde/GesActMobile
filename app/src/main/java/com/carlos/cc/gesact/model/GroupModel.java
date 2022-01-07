package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GroupModel {

    @PrimaryKey (autoGenerate = true)
    public int groupId;

    @ColumnInfo (name = "group_name")
    public  String groupName;

    @ColumnInfo (name = "grade_name")
    public String grade_name;

    @ColumnInfo (name = "group_status")
    public boolean groupStatus;

    @Embedded(prefix = "group")
    public CriterionModel CriterionModel;

}

