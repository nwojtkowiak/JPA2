package com.capgemini.dao;

import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainingQueryDao  {

     long sumCostAllTrainingForStudent(long student);
}
