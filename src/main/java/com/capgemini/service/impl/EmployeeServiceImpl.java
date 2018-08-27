package com.capgemini.service.impl;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.StudentDao;
import com.capgemini.dao.TrainerDao;
import com.capgemini.dao.TrainingDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import com.capgemini.exceptions.ProblemWithAddStudent;
import com.capgemini.exceptions.ProblemWithAddTrener;
import com.capgemini.mappers.EmployeeMapper;
import com.capgemini.mappers.StudentMapper;
import com.capgemini.mappers.TrainerMapper;
import com.capgemini.mappers.TrainingMapper;
import com.capgemini.service.EmployeeService;
import com.capgemini.service.TrainingService;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private TrainerDao trainerDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TrainingDao trainingDao;


    @Override
    public EmployeeTO addEmployee(EmployeeTO employee) {
        EmployeeEntity employeeEntity = EmployeeMapper.toEntity(employee);
        employeeEntity = employeeDao.save(employeeEntity);
        return EmployeeMapper.toTO(employeeEntity);
    }

    @Override
    public void delEmployee(long id) throws NotFoundException {
        Optional<EmployeeEntity> employeeEntity = employeeDao.findById(id);
        if(employeeEntity.isPresent()) {
            StudentEntity student = employeeEntity.get().getStudent();
            TrainerEntity trainer = employeeEntity.get().getTrainer();

            List<TrainingEntity> trainingEntitiesStudent = trainingDao.findByStudentsContains(student);
            for(TrainingEntity t : trainingEntitiesStudent){
                t.getStudents().remove(student);
                trainingDao.save(t);
            }
            List<TrainingEntity> trainingEntitiesTrainer = trainingDao.findByTrainersContains(trainer);
            for(TrainingEntity t : trainingEntitiesTrainer){
                t.getTrainers().remove(trainer);
                trainingDao.save(t);
            }

            employeeDao.deleteById(id);
        }else{
            throw new NotFoundException("Employee isn't exist");
        }

    }

    @Override
    public void delStudent(long id) throws NotFoundException {
        Optional<StudentEntity> studentEntity = studentDao.findById(id);
        if(studentEntity.isPresent()) {
            List<TrainingEntity> trainingEntitiesStudent = trainingDao.findByStudentsContains(studentEntity.get());
            for(TrainingEntity t : trainingEntitiesStudent){
                t.getStudents().remove(studentEntity.get());
                trainingDao.save(t);
            }
            studentDao.deleteById(id);
        }else{
            throw new NotFoundException("Student isn't exist");
        }

    }

    @Override
    public void delTrainer(long id) throws NotFoundException {
        Optional<TrainerEntity> trainerEntity = trainerDao.findById(id);
        if(trainerEntity.isPresent()) {
            List<TrainingEntity> trainingEntitiesTrainer = trainingDao.findByTrainersContains(trainerEntity.get());
            for(TrainingEntity t : trainingEntitiesTrainer){
                t.getTrainers().remove(trainerEntity.get());
                trainingDao.save(t);
            }
            trainerDao.deleteById(id);
        }else{
            throw new NotFoundException("Trainer isn't exist");
        }
    }

    @Override
    public EmployeeTO updateEmployee(EmployeeTO employee) throws NotFoundException {
        EmployeeEntity employeeEntity = EmployeeMapper.toEntity(employee);
        if(employeeEntity.getId() == null || !employeeDao.findById(employeeEntity.getId()).isPresent()){
            throw new NotFoundException("Employee isn't exist");
        }
        employeeEntity = employeeDao.save(employeeEntity);
        return EmployeeMapper.toTO(employeeEntity);
    }

    @Override
    public StudentTO updateStudent(StudentTO student) throws NotFoundException {
        StudentEntity studentEntity = StudentMapper.toEntity(student);
        if(studentEntity.getId() == null || !studentDao.findById(studentEntity.getId()).isPresent()){
            throw new NotFoundException("Student isn't exist");
        }
        studentEntity = studentDao.save(studentEntity);
        return StudentMapper.toTO(studentEntity);
    }

    @Override
    public TrainerTO updateTrainer(TrainerTO trainer) throws NotFoundException {
        TrainerEntity trainerEntity = TrainerMapper.toEntity(trainer);
        if(trainerEntity.getId() == null || !trainerDao.findById(trainerEntity.getId()).isPresent()){
            throw new NotFoundException("Trainer isn't exist");
        }
        trainerEntity = trainerDao.save(trainerEntity);
        return TrainerMapper.toTO(trainerEntity);
    }

    @Override
    public TrainerTO addInternalTrainer(EmployeeTO employee) throws ProblemWithAddTrener {

        TrainerEntity trainerEntity = new TrainerEntity(employee.getFirstName(), employee.getLastName(), employee.getPosition());
        trainerEntity = trainerDao.save(trainerEntity);

        if (trainerEntity.getId() != null) {
            EmployeeEntity employeeEntity = employeeDao.findById(employee.getId()).get();
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
            bossEntity = employeeDao.findById(boss.getId()).get();
            studentEntity = new StudentEntity(employee.getFirstName(), employee.getLastName(),
                    employee.getPosition(), grade, bossEntity);
        }else {
            studentEntity = new StudentEntity(employee.getFirstName(), employee.getLastName(),
                    employee.getPosition(), grade);
        }

        studentEntity = studentDao.save(studentEntity);

        if (studentEntity.getId() != null) {
            EmployeeEntity employeeEntity = employeeDao.findById(employee.getId()).get();
            employeeEntity.setStudent(studentEntity);
            return StudentMapper.toTO(studentDao.save(studentEntity));
        }
        throw new ProblemWithAddStudent();

    }

    @Override
    public EmployeeTO findEmployee(long id) {
        Optional<EmployeeEntity> employeeEntity =  employeeDao.findById(id);
        return employeeEntity.map(EmployeeMapper::toTO).orElse(null);
    }

    @Override
    public StudentTO findStudent(long studentId) {
        Optional<StudentEntity> studentEntity =  studentDao.findById(studentId);
        return studentEntity.map(StudentMapper::toTO).orElse(null);
    }

    @Override
    public TrainerTO findTrainer(long trainerId) {
        Optional<TrainerEntity> trainerEntity =  trainerDao.findById(trainerId);
        return trainerEntity.map(TrainerMapper::toTO).orElse(null);
    }

    @Override
    public EmployeeTO findEmployeeByStudent(long studentId) {
        StudentEntity studentEntity = studentDao.findById(studentId).get();;
        return EmployeeMapper.toTO(employeeDao.findByStudent(studentEntity));
    }

    @Override
    public EmployeeTO findEmployeeByTrainer(long trainerId) {
        TrainerEntity trainerEntity = trainerDao.findById(trainerId).get();;
        return EmployeeMapper.toTO(employeeDao.findByTrainer(trainerEntity));
    }

    @Override
    public boolean compareTrainersAndStudents(List<TrainerEntity> trainers, List<StudentEntity> students) {

        List<EmployeeEntity> employeeEntities = employeeDao.findByTrainerInAndStudentIn(trainers, students);

        return employeeEntities.size() > 0;

    }
}
