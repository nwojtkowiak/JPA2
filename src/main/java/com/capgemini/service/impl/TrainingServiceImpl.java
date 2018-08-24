package com.capgemini.service.impl;

import com.capgemini.dao.StudentDao;
import com.capgemini.dao.TrainerDao;
import com.capgemini.dao.TrainingDao;
import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import com.capgemini.exceptions.ParticipationInCourseException;
import com.capgemini.exceptions.TooLargeTotalAmountException;
import com.capgemini.exceptions.TooMuchTrainingException;
import com.capgemini.mappers.StudentMapper;
import com.capgemini.mappers.TrainerMapper;
import com.capgemini.mappers.TrainingMapper;
import com.capgemini.service.EmployeeService;
import com.capgemini.service.TrainingService;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private TrainerDao trainerDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private EmployeeService employeeService;

    @Override
    @Transactional(readOnly = false)
    public TrainingTO addTraining(TrainingTO training) {
        TrainingEntity trainingEntity = TrainingMapper.toEntity(training);

        List<StudentEntity> students = new ArrayList<>();
        for (Long studentId : training.getStudents()) {
            StudentEntity student = studentDao.findOne(studentId);
            students.add(student);
        }

        List<TrainerEntity> trainers = new ArrayList<>();
        for (Long trainerId : training.getTrainers()) {
            TrainerEntity trainer = trainerDao.findOne(trainerId);
            trainers.add(trainer);
        }

        trainingEntity.setStudents(students);
        trainingEntity.setTrainers(trainers);

        return TrainingMapper.toTO(trainingDao.save(trainingEntity));

    }

    @Override
    @Transactional(readOnly = false)
    public TrainingTO updateTraining(TrainingTO training) //throws OptimisticLockingFailureException
    {

        TrainingEntity trainingEntity = TrainingMapper.toEntity(training);

        List<StudentEntity> students = new ArrayList<>();
        for (Long studentId : training.getStudents()) {
            StudentEntity student = studentDao.findOne(studentId);
            students.add(student);
        }

        List<TrainerEntity> trainers = new ArrayList<>();
        for (Long trainerId : training.getTrainers()) {
            TrainerEntity trainer = trainerDao.findOne(trainerId);
            trainers.add(trainer);
        }

        trainingEntity.setStudents(students);
        trainingEntity.setTrainers(trainers);

        //if(training.getVersion() == trainingEntity.getVersion()) {
        trainingEntity = trainingDao.save(trainingEntity);
        return TrainingMapper.toTO(trainingEntity);
        //  }

        //   throw new OptimisticLockingFailureException("updateTraining");

    }

    @Override
    public TrainingTO findTraining(long id) {
        TrainingEntity trainingEntity = trainingDao.findOne(id);

        return TrainingMapper.toTO(trainingEntity);
    }

    @Override
    public List<TrainingTO> findTrainings() {
        return TrainingMapper.map2TOs(trainingDao.findAll());
    }

    @Override
    public List<TrainerTO> findTrainers(TrainingTO training) {
        TrainingEntity trainingEntity = trainingDao.findOne(training.getId());
        return TrainerMapper.map2TOs(trainingEntity.getTrainers());
    }

    @Override
    public List<StudentTO> findStudents(TrainingTO training) {
        TrainingEntity trainingEntity = trainingDao.findOne(training.getId());
        return StudentMapper.map2TOs(trainingEntity.getStudents());
    }




    @Override
    @Transactional(readOnly = false)
    public TrainingTO addTrainerToTraining(TrainingTO training, TrainerTO trainer) throws ParticipationInCourseException {
        TrainerEntity trainerEntity = trainerDao.findOne(trainer.getId());

        if (trainerEntity.getId() != null) {
            TrainingEntity trainingEntity = trainingDao.findOne(training.getId());

            trainingEntity.getTrainers().add(trainerEntity);
            if (trainingEntity.getStudents().size() > 0) {
                if (employeeService.compareTrainersAndStudents(trainingEntity.getTrainers(), trainingEntity.getStudents())) {
                    trainingEntity.getTrainers().remove(trainerEntity);
                    throw new ParticipationInCourseException();
                }
            }


            return TrainingMapper.toTO(trainingDao.save(trainingEntity));
        }
        return training;
    }

    @Override
    @Transactional(readOnly = false)
    public TrainingTO addStudentToTraining(TrainingTO training, StudentTO student) throws ParticipationInCourseException, TooLargeTotalAmountException, TooMuchTrainingException {
        StudentEntity studentEntity = studentDao.findOne(student.getId());

        if (studentEntity.getId() != null) {
            TrainingEntity trainingEntity = trainingDao.findOne(training.getId());

            double sum = sumAllCostForStudentInThisYear(studentEntity.getId());
            sum += training.getAmount();
            int countTraining = countAllTrainingForStudentInThisYear(studentEntity.getId());

            checkCorrectSum(sum, studentEntity.getGrade(), countTraining + 1);

            trainingEntity.getStudents().add(studentEntity);
            if (trainingEntity.getTrainers().size() > 0) {
                if (employeeService.compareTrainersAndStudents(trainingEntity.getTrainers(), trainingEntity.getStudents())) {
                    trainingEntity.getStudents().remove(studentEntity);
                    throw new ParticipationInCourseException();
                }
            }

            return TrainingMapper.toTO(trainingDao.save(trainingEntity));
        }

        return training;
    }


    @Override
    public double sumAllCostForStudent(long studentId) {
        return trainingDao.sumCostAllTrainingForStudent(studentId);

    }

    @Override
    public double sumAllCostForStudentInThisYear(long studentId) {

        Date dtFrom = Date.valueOf(setStartDateThisYear());
        Date dtTo = Date.valueOf(setEndDateThisYear());

        return trainingDao.sumCostAllTrainingForStudentPerYear(studentId, dtFrom, dtTo);

    }

    @Override
    public int countAllTrainingForStudentInThisYear(long studentId) {
        Date dtFrom = Date.valueOf(setStartDateThisYear());
        Date dtTo = Date.valueOf(setEndDateThisYear());

        return trainingDao.countAllTrainingForStudentPerYear(studentId, dtFrom, dtTo);

    }


    private void checkCorrectSum(Double sum, int grade, long countTraining) throws TooLargeTotalAmountException, TooMuchTrainingException {
        if (grade >= 4) {
            if (sum > 50000) {
                throw new TooLargeTotalAmountException();
            }
        } else {
            if (sum > 15000) {
                throw new TooLargeTotalAmountException();
            }
            if (countTraining > 3) {
                throw new TooMuchTrainingException();
            }
        }
    }

    private String setStartDateThisYear(){
        int year = LocalDate.now().getYear();
        LocalDate date = LocalDate.of(year, 1,1);
        return date.toString();
    }

    private String setEndDateThisYear(){
        int year = LocalDate.now().getYear();
        LocalDate date = LocalDate.of(year, 12,31);
        return date.toString();
    }
}
