package com.capgemini.dao;

import com.capgemini.domain.EmployeeEntity;

/*

*/
public interface EmployeeDao extends Dao<EmployeeEntity, Long> {

    EmployeeEntity add(EmployeeEntity entity);



}
