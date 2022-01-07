package com.carlos.cc.gesact.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.carlos.cc.gesact.model.CriterionModel;

import java.util.List;

@Dao
public interface CriterionDao {

    @Query("SELECT * FROM criterionmodel")
    List<CriterionModel> getAll();

    @Query("SELECT * FROM criterionmodel WHERE criterionId = :criterionId")
    CriterionModel getCriterion(int criterionId);

    @Insert
    void insertAll(CriterionModel...criterionModels);

    @Update
    void update(CriterionModel criterionModel);

    @Query("DELETE FROM criterionmodel WHERE criterionId = :criterionId")
    void delete(int criterionId);


}
