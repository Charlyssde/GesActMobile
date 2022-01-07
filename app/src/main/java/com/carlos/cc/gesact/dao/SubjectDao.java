package com.carlos.cc.gesact.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.carlos.cc.gesact.model.SubjectModel;

import java.util.List;

@Dao
public interface SubjectDao {

    @Query("SELECT * FROM subjectmodel")
    List<SubjectModel> getAll();

    @Query("SELECT * FROM subjectmodel INNER JOIN groupmodel WHERE subjectgroupId = :groupId")
    List<SubjectModel> getByGroup(int groupId);

    @Insert
    void insertAll(SubjectModel...subjectModels);

    @Query("DELETE FROM subjectmodel WHERE subject_id = :subjectId")
    void delete(int subjectId);

}
