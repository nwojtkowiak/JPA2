package com.capgemini.dao;

import com.capgemini.domain.StudentEntity;
import org.springframework.data.repository.CrudRepository;

public interface StudentDao extends CrudRepository<StudentEntity, Long> {

}
