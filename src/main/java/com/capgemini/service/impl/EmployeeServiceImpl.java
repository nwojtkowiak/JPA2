package com.capgemini.service.impl;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.StudentDao;
import com.capgemini.dao.TrainerDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.mappers.EmployeeMapper;
import com.capgemini.mappers.StudentMapper;
import com.capgemini.mappers.TrainerMapper;
import com.capgemini.service.EmployeeService;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private TrainerDao trainerDao;

    @Autowired
    private StudentDao studentDao;


    @Override
    public EmployeeTO addEmployee(EmployeeTO employee) {
        EmployeeEntity employeeEntity = EmployeeMapper.toEntity(employee);
        employeeEntity = employeeDao.save(employeeEntity);
        return EmployeeMapper.toTO(employeeEntity);
    }

    @Override
    public EmployeeTO updateEmployee(EmployeeTO employee) {
        EmployeeEntity employeeEntity = EmployeeMapper.toEntity(employee);
        employeeEntity = employeeDao.save(employeeEntity);
        return EmployeeMapper.toTO(employeeEntity);
    }

    @Override
    @Transactional
    public TrainerTO addTrainer(EmployeeTO employee, TrainerTO trainer) {
        TrainerEntity trainerEntity = TrainerMapper.toEntity(trainer);
        trainerEntity = trainerDao.save(trainerEntity);

        if(trainerEntity.getId() != null && trainerEntity.getCompanyName().length() == 0) {
            EmployeeEntity employeeEntity = employeeDao.findOne(employee.getId());
            employeeEntity.setTrainer(trainerEntity);
            employeeDao.save(employeeEntity);
            return TrainerMapper.toTO(trainerEntity);
        }

        return TrainerMapper.toTO(trainerEntity);
    }

    @Override
    @Transactional
    public StudentTO addStudent(EmployeeTO employee, StudentTO student) {
        StudentEntity studentEntity = StudentMapper.toEntity(student);
        studentEntity = studentDao.save(studentEntity);

        if(studentEntity.getId() != null) {
            EmployeeEntity employeeEntity = employeeDao.findOne(employee.getId());
            employeeEntity.setStudent(studentEntity);
            return StudentMapper.toTO(studentDao.save(studentEntity));
        }

        return student;
    }

    @Override
    public EmployeeTO findEmployee(long id) {
        return EmployeeMapper.toTO(employeeDao.findOne(id));
    }

    @Override
    public boolean compareTrainersAndStudents(List<TrainerEntity> trainers, List<StudentEntity> students) {

        List<EmployeeEntity> employeeEntities = employeeDao.findByTrainerInAndStudentIn(trainers, students);

        return employeeEntities.size() > 0;

    }
}
