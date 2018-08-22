package com.capgemini.dao;

import com.capgemini.domain.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

/*

*/
public interface EmployeeDao extends CrudRepository<EmployeeEntity, Long>{



}
