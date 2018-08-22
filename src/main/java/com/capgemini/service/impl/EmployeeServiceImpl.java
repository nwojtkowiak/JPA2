package com.capgemini.service.impl;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.StudentDao;
import com.capgemini.dao.TrainerDao;
import com.capgemini.dao.TrainingDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import com.capgemini.mappers.EmployeeMapper;
import com.capgemini.mappers.StudentMapper;
import com.capgemini.mappers.TrainerMapper;
import com.capgemini.mappers.TrainingMapper;
import com.capgemini.service.EmployeeService;
import com.capgemini.service.TrainingService;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;




    @Override
    @Transactional
    public EmployeeTO addTrainer(EmployeeTO employee, TrainerTO trainer) {
        TrainerEntity trainerEntity = TrainerMapper.toEntity(trainer);

        if(trainerEntity.getId() != null) {
            EmployeeEntity employeeEntity = employeeDao.findOne(employee.getId());
            employeeEntity.getTrainers().add(trainerEntity);
            return EmployeeMapper.toTO(employeeDao.save(employeeEntity));
        }

        return employee;
    }

    @Override
    @Transactional
    public EmployeeTO addStudent(EmployeeTO employee,StudentTO student) {
        StudentEntity studentEntity = StudentMapper.toEntity(student);

        if(studentEntity.getId() != null) {
            EmployeeEntity employeeEntity = employeeDao.findOne(employee.getId());
            employeeEntity.getStudents().add(studentEntity);
            return EmployeeMapper.toTO(employeeDao.save(employeeEntity));
        }

        return employee;
    }
}
