package com.capgemini.dao;

import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*

*/
public interface EmployeeDao extends CrudRepository<EmployeeEntity, Long>{

    List<EmployeeEntity> findByTrainerInAndStudentIn(List<TrainerEntity> trainers, List<StudentEntity> students);

}
