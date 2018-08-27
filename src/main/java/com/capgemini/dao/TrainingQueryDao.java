package com.capgemini.dao;

import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import com.capgemini.types.TrainingSearchCriteriaTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainingQueryDao  {

     List<TrainingEntity> findTrainingsByCriteria(TrainingSearchCriteriaTO criteria);
     List<StudentEntity> findStudentsWithLongestDuration();
     List<TrainingEntity> findTrainingWithMostEditions();
}
