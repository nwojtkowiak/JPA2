package com.capgemini.dao;

import com.capgemini.domain.TrainerEntity;
import org.springframework.data.repository.CrudRepository;

public interface TrainerDao extends CrudRepository<TrainerEntity, Long> {

}
