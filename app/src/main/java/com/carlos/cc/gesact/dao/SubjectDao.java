package com.carlos.cc.gesact.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.carlos.cc.gesact.adapters.SubjectsAdapter;
import com.carlos.cc.gesact.model.SubjectModel;

import java.util.List;

@Dao
public interface SubjectDao {

    @Query("SELECT * FROM subjectmodel")
    List<SubjectModel> getAll();

    @Query("SELECT * FROM subjectmodel JOIN groupmodel WHERE subjectgroupId = :groupId")
    List<SubjectModel> getByGroup(int groupId);

    @Insert
    void insertAll(SubjectModel...subjectModels);

    @Update
    void update(SubjectModel subjectModel);

    @Query("DELETE FROM subjectmodel WHERE subject_id = :subjectId")
    void delete(int subjectId);

}
