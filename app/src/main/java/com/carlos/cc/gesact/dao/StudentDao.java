package com.carlos.cc.gesact.dao;

import androidx.room.Dao;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.carlos.cc.gesact.model.StudentModel;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM studentmodel")
    List<StudentModel> getAll();

    @Query("SELECT * FROM studentmodel WHERE student_grade = :grade and student_group = :group")
    List<StudentModel> getByGradeGroup(String grade, String group);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(StudentModel... studentModels);


}
