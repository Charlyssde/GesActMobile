package com.carlos.cc.gesact.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.carlos.cc.gesact.model.StudentModel;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM studentmodel")
    List<StudentModel> getAll();




}
