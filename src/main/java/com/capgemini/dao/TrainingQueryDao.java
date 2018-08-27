package com.capgemini.dao;

import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainingEntity;
import com.capgemini.types.TrainingSearchCriteriaTO;

import java.util.List;

public interface TrainingQueryDao {

    List<TrainingEntity> findTrainingsByCriteria(TrainingSearchCriteriaTO criteria);

    List<StudentEntity> findStudentsWithLongestDuration();

    List<TrainingEntity> findTrainingWithMostEditions();
}
