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
import com.capgemini.types.TrainingSearchCriteriaTO;
import com.capgemini.types.TrainingTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public TrainingTO addTraining(TrainingTO training) throws ParticipationInCourseException, TooLargeTotalAmountException, TooMuchTrainingException {
        TrainingEntity trainingEntity = TrainingMapper.toEntity(training);

        List<StudentEntity> students = new ArrayList<>();
        for (Long studentId : training.getStudents()) {
            StudentEntity student = studentDao.findById(studentId).get();
            checkConditionsForStudent(trainingEntity, student);
            students.add(student);
            if (!checkTrainersAndStudents(trainingEntity)) {
                students.remove(student);
                throw new ParticipationInCourseException();
            }

        }

        List<TrainerEntity> trainers = new ArrayList<>();
        for (Long trainerId : training.getTrainers()) {
            TrainerEntity trainer = trainerDao.findById(trainerId).get();
            trainers.add(trainer);
            if (!checkTrainersAndStudents(trainingEntity)) {
                trainers.remove(trainer);
                throw new ParticipationInCourseException();
            }
        }

        trainingEntity.setStudents(students);
        trainingEntity.setTrainers(trainers);

        return TrainingMapper.toTO(trainingDao.save(trainingEntity));

    }

    @Override
    public void delTraining(long id) throws NotFoundException {
        if (trainingDao.findById(id).isPresent()) {
            trainingDao.deleteById(id);
        } else {
            throw new NotFoundException("Training isn't exist");
        }
    }

    @Override
    @Transactional(readOnly = false)
    public TrainingTO updateTraining(TrainingTO training) throws TooLargeTotalAmountException, TooMuchTrainingException, ParticipationInCourseException, NotFoundException {

        if (training.getId() != null) {
            TrainingEntity trainingEntity = TrainingMapper.toEntity(training);

            List<TrainerEntity> trainers = new ArrayList<>();
            for (Long trainerId : training.getTrainers()) {
                TrainerEntity trainer = trainerDao.findById(trainerId).get();
                trainers.add(trainer);
                if (!checkTrainersAndStudents(trainingEntity)) {
                    trainers.remove(trainer);
                    throw new ParticipationInCourseException();
                }
            }

            List<StudentEntity> students = new ArrayList<>();
            for (Long studentId : training.getStudents()) {
                StudentEntity student = studentDao.findById(studentId).get();
                checkConditionsForStudent(trainingEntity, student);
                students.add(student);
                if (!checkTrainersAndStudents(trainingEntity)) {
                    students.remove(student);
                    throw new ParticipationInCourseException();
                }

            }

            trainingEntity.setStudents(students);
            trainingEntity.setTrainers(trainers);
            trainingEntity = trainingDao.save(trainingEntity);
            return TrainingMapper.toTO(trainingEntity);
        }
        throw new NotFoundException("Training isn't exist");
    }

    @Override
    public TrainingTO findTraining(long id) {
        Optional<TrainingEntity> trainingEntity = trainingDao.findById(id);
        return trainingEntity.map(TrainingMapper::toTO).orElse(null);
    }

    @Override
    public List<TrainingTO> findTrainings() {
        return TrainingMapper.map2TOs(trainingDao.findAll());
    }

    @Override
    public List<TrainingTO> findTrainingsByKeyWord(String key) {
        return TrainingMapper.map2TOs(trainingDao.findByKeyWordsContains(key));
    }

    @Override
    public List<TrainingTO> findTrainingsBySearchCriteria(TrainingSearchCriteriaTO criteria) {
        return TrainingMapper.map2TOs(trainingDao.findTrainingsByCriteria(criteria));
    }

    @Override
    public List<TrainingTO> findTrainingsWithMostEdition() {
        return TrainingMapper.map2TOs(trainingDao.findTrainingWithMostEditions());
    }

    @Override
    public List<StudentTO> findStudentsWithLongestDuration() {
        return StudentMapper.map2TOs(trainingDao.findStudentsWithLongestDuration());
    }

    @Override
    public List<TrainerTO> findTrainers(TrainingTO training) {
        TrainingEntity trainingEntity = trainingDao.findById(training.getId()).get();
        return TrainerMapper.map2TOs(trainingEntity.getTrainers());
    }

    @Override
    public List<StudentTO> findStudents(TrainingTO training) {
        TrainingEntity trainingEntity = trainingDao.findById(training.getId()).get();
        return StudentMapper.map2TOs(trainingEntity.getStudents());
    }


    @Override
    @Transactional(readOnly = false)
    public TrainingTO addTrainerToTraining(TrainingTO training, long trainerId) throws ParticipationInCourseException {
        TrainerEntity trainerEntity = trainerDao.findById(trainerId).get();
        if (trainerEntity.getId() != null) {
            TrainingEntity trainingEntity;
            if (training.getId() != null) {
                trainingEntity = trainingDao.findById(training.getId()).get();
            } else {
                trainingEntity = TrainingMapper.toEntity(training);
            }

            trainingEntity.getTrainers().add(trainerEntity);
            if (!checkTrainersAndStudents(trainingEntity)) {
                trainingEntity.getTrainers().remove(trainerEntity);
                throw new ParticipationInCourseException();
            }
            return TrainingMapper.toTO(trainingDao.save(trainingEntity));
        }
        return training;
    }

    @Override
    @Transactional(readOnly = false)
    public TrainingTO addStudentToTraining(TrainingTO training, long studentId) throws ParticipationInCourseException, TooLargeTotalAmountException, TooMuchTrainingException {
        StudentEntity studentEntity = studentDao.findById(studentId).get();

        if (studentEntity.getId() != null) {
            TrainingEntity trainingEntity;
            if (training.getId() != null) {
                trainingEntity = trainingDao.findById(training.getId()).get();
            } else {
                trainingEntity = TrainingMapper.toEntity(training);
            }

            checkConditionsForStudent(trainingEntity, studentEntity);

            trainingEntity.getStudents().add(studentEntity);
            if (!checkTrainersAndStudents(trainingEntity)) {
                trainingEntity.getStudents().remove(studentEntity);
                throw new ParticipationInCourseException();
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

    @Override
    public int sumHoursAllTrainingForTrainerInThisYear(long trainerId) {
        Date dtFrom = Date.valueOf(setStartDateThisYear());
        Date dtTo = Date.valueOf(setEndDateThisYear());

        return trainingDao.sumHoursAllTrainingForTrainerPerYear(trainerId, dtFrom, dtTo);
    }

    @Override
    public int countAllTrainingForEmployeeInPeriod(long employeeId, Date dtFrom, Date dtTo) {
        return trainingDao.countAllTrainingForEmployeeInPeriod(employeeId, dtFrom, dtTo);
    }


    /**
     * This method check conditions for a student.
     * If student has grade greater or equal than 4 then student has limit to 50000 in this year on training.
     * Else student has limit to 15000 and for three trainings in this year
     * @param sum - sum of amount all trainings for student
     * @param grade - students's grade
     * @param countTraining - count of training in this year
     * @throws TooLargeTotalAmountException - if student wants to take part in trainings which exceeds limit
     * @throws TooMuchTrainingException - if student wants to tak part to much trainings
     */
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

    /**
     * This method counts all conditions for student - sum of amount all trainings
     * and count of trainings
     * @param trainingEntity - training in which wants take part now
     * @param studentEntity - student who wants take part in training
     * @throws TooLargeTotalAmountException
     * @throws TooMuchTrainingException
     */
    private void checkConditionsForStudent(TrainingEntity trainingEntity, StudentEntity studentEntity) throws TooLargeTotalAmountException, TooMuchTrainingException {
        double sum = sumAllCostForStudentInThisYear(studentEntity.getId());
        sum += trainingEntity.getAmount();
        int countTraining = countAllTrainingForStudentInThisYear(studentEntity.getId());
        checkCorrectSum(sum, studentEntity.getGrade(), countTraining + 1);
    }

    /**
     * This method gets start of this year - year-01-01
     * @return start of this year as String
     */
    private String setStartDateThisYear() {
        int year = LocalDate.now().getYear();
        LocalDate date = LocalDate.of(year, 1, 1);
        return date.toString();
    }

    /**
     * This method checks that lists of trainers and students are separable for one training
     * @param trainingEntity - training for which check part trainers and students
     * @return true if lists are separable or false if not
     */
    private boolean checkTrainersAndStudents(TrainingEntity trainingEntity) {
        if (trainingEntity.getStudents().size() > 0) {
            if (employeeService.compareTrainersAndStudents(trainingEntity.getTrainers(), trainingEntity.getStudents())) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method gets end of this year - year-12-31
     * @return end of this year as String
     */
    private String setEndDateThisYear() {
        int year = LocalDate.now().getYear();
        LocalDate date = LocalDate.of(year, 12, 31);
        return date.toString();
    }
}
