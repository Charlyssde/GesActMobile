package com.carlos.cc.gesact.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GroupModel {

    public static final List<GroupModel> ITEMS = new ArrayList<GroupModel>();

    private static void addItem(GroupModel item) {
        ITEMS.add(item);
    }

    private static GroupModel createItem(int i) {
        return new GroupModel("Grupo " + i, "A", true);
    }

    static {
        // Add some sample items.
        for (int i = 1; i <= 25; i++) {
            addItem(createItem(i));
        }
    }

    public GroupModel(){}

    public GroupModel (String groupName, String grade_name, boolean groupStatus){
        this.groupName = groupName;
        this.gradeName = grade_name;
        this.groupStatus = groupStatus;
    }

    @PrimaryKey (autoGenerate = true)
    public int groupId;

    @ColumnInfo (name = "group_name")
    public  String groupName;

    @ColumnInfo (name = "grade_name")
    public String gradeName;

    @ColumnInfo (name = "group_status")
    public boolean groupStatus;

}

