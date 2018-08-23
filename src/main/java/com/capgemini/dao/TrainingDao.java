package com.capgemini.dao;

import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingDao extends CrudRepository<TrainingEntity, Long> {

     List<TrainingEntity> findAll();

     List<TrainerEntity> findTrainersById(long id);

     List<TrainingEntity> findByStudentsContains(StudentEntity student);

    // @Query("select sum(t.amount) from TrainingEntity t where :student member of t.students  ")
    @Query("select sum(t.amount) from TrainingEntity t join t.students s on s.id = :student  ")
     Long sumCostAllTrainingForStudent(@Param("student") long id /*StudentEntity student*/);
}
