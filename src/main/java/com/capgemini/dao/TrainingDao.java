package com.capgemini.dao;

import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainingDao extends CrudRepository<TrainingEntity, Long> {

     List<TrainingEntity> findAll();

     List<TrainerEntity> findTrainersById(long id);
}
