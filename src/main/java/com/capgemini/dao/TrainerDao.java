package com.capgemini.dao;

import com.capgemini.domain.TrainerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainerDao extends CrudRepository<TrainerEntity, Long> {

    List<TrainerEntity> findByIdIn(List<Long> ids);

}
