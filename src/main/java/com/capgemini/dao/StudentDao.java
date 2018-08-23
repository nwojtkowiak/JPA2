package com.capgemini.dao;

import com.capgemini.domain.StudentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentDao extends CrudRepository<StudentEntity, Long> {

    List<StudentEntity> findByIdIn(List<Long> ids);
}
