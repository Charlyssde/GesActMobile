package com.carlos.cc.gesact.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.carlos.cc.gesact.model.StudentModel;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM studentmodel")
    List<StudentModel> getAll();

    @Query("SELECT * FROM studentmodel INNER JOIN groupmodel WHERE studentgroupId = :studentgroupId ")
    List<StudentModel> getByGroup(int studentgroupId);

    @Query("SELECT * FROM studentmodel WHERE student_id = :studentId LIMIT 1")
    StudentModel getStudent(int studentId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(StudentModel... studentModels);

    @Update
    void update(StudentModel studentModel);

    @Query("DELETE FROM studentmodel WHERE student_id = :studentId")
    void delete(int studentId);

}
