package com.capgemini.dao;

import com.capgemini.domain.TrainingEntity;
import org.springframework.data.repository.CrudRepository;

public interface TrainingDao extends CrudRepository<TrainingEntity, Long> {

}
