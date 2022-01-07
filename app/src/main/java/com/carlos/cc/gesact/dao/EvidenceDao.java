package com.carlos.cc.gesact.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.carlos.cc.gesact.model.EvidenceModel;

import java.util.List;

@Dao
public interface EvidenceDao {

    @Query("SELECT * FROM evidencemodel")
    List<EvidenceModel> getAll();

    @Query("SELECT * FROM evidencemodel JOIN studentmodel WHERE student_id = :studentId AND evidence_kind = :evidenceKind")
    List<EvidenceModel> getByStudentByKind(int studentId, String evidenceKind);

    @Query("SELECT * FROM evidencemodel JOIN studentmodel WHERE student_id = :studentId")
    List<EvidenceModel> getAllByStudent(int studentId);

    @Query("SELECT * FROM evidencemodel WHERE evidence_id = :evidenceId LIMIT 1")
    EvidenceModel getEvidence(int evidenceId);

    @Insert
    void insertAll(EvidenceModel... evidenceModel);

    @Query("DELETE FROM evidencemodel WHERE evidence_id = :evidenceId")
    void delete(int evidenceId);

}
