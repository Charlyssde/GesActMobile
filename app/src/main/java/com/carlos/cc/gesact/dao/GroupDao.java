package com.carlos.cc.gesact.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;

import com.carlos.cc.gesact.model.GroupModel;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("SELECT * FROM groupmodel")
    List<GroupModel> getAll();

    @Query("SELECT * FROM groupmodel WHERE groupId = :groupId")
    GroupModel getGroup(int groupId);

    @Update
    void update(GroupModel groupModel);

    @Insert
    void insertAll(GroupModel...groupModels);

    @Query("DELETE FROM groupmodel WHERE groupId = :groupId")
    void delete(int groupId);

}
