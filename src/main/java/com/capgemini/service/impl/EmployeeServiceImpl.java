package com.capgemini.service.impl;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.StudentDao;
import com.capgemini.dao.TrainerDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.exceptions.ProblemWithAddStudent;
import com.capgemini.exceptions.ProblemWithAddTrener;
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
    public TrainerTO addInternalTrainer(EmployeeTO employee) throws ProblemWithAddTrener {

        TrainerEntity trainerEntity = new TrainerEntity(employee.getFirstName(), employee.getLastName(), employee.getPosition());
        trainerEntity = trainerDao.save(trainerEntity);

        if (trainerEntity.getId() != null) {
            EmployeeEntity employeeEntity = employeeDao.findOne(employee.getId());
            employeeEntity.setTrainer(trainerEntity);
            employeeDao.save(employeeEntity);
            return TrainerMapper.toTO(trainerEntity);
        }

        throw new ProblemWithAddTrener();
    }

    @Override
    public TrainerTO addExternalTrainer(TrainerTO trainer) throws ProblemWithAddTrener {
        if(trainer.getCompanyName().length() > 0) {
            TrainerEntity trainerEntity = TrainerMapper.toEntity(trainer);
            trainerEntity = trainerDao.save(trainerEntity);
            return TrainerMapper.toTO(trainerEntity);
        }
        throw new ProblemWithAddTrener();

    }


    @Override
    @Transactional
    public StudentTO addStudent(EmployeeTO employee, int grade, EmployeeTO boss) throws ProblemWithAddStudent {

        EmployeeEntity bossEntity;
        StudentEntity studentEntity;

        if(boss != null){
            bossEntity = employeeDao.findOne(boss.getId());
            studentEntity = new StudentEntity(employee.getFirstName(), employee.getLastName(),
                    employee.getPosition(), grade, bossEntity);
        }else {
            studentEntity = new StudentEntity(employee.getFirstName(), employee.getLastName(),
                    employee.getPosition(), grade);
        }

        studentEntity = studentDao.save(studentEntity);

        if (studentEntity.getId() != null) {
            EmployeeEntity employeeEntity = employeeDao.findOne(employee.getId());
            employeeEntity.setStudent(studentEntity);
            return StudentMapper.toTO(studentDao.save(studentEntity));
        }
        throw new ProblemWithAddStudent();

    }

    @Override
    public EmployeeTO findEmployee(long id) {
        return EmployeeMapper.toTO(employeeDao.findOne(id));
    }

    @Override
    public EmployeeEntity findEmployeeByStudent(long student_id) {
        StudentEntity studentEntity = studentDao.findOne(student_id);
        return employeeDao.findByStudent(studentEntity);
    }

    @Override
    public boolean compareTrainersAndStudents(List<TrainerEntity> trainers, List<StudentEntity> students) {

        List<EmployeeEntity> employeeEntities = employeeDao.findByTrainerInAndStudentIn(trainers, students);

        return employeeEntities.size() > 0;

    }
}
