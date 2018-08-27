package com.capgemini.service;

import com.capgemini.exceptions.ParticipationInCourseException;
import com.capgemini.exceptions.TooLargeTotalAmountException;
import com.capgemini.exceptions.TooMuchTrainingException;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingSearchCriteriaTO;
import com.capgemini.types.TrainingTO;
import javassist.NotFoundException;
import org.springframework.dao.OptimisticLockingFailureException;

import java.sql.Date;
import java.util.List;

public interface TrainingService {

    TrainingTO addTraining(TrainingTO training) throws ParticipationInCourseException, TooLargeTotalAmountException, TooMuchTrainingException;

    void delTraining(long id) throws NotFoundException;

    TrainingTO updateTraining(TrainingTO training) throws OptimisticLockingFailureException, TooLargeTotalAmountException, TooMuchTrainingException, ParticipationInCourseException, NotFoundException;

    TrainingTO findTraining(long id);

    List<TrainingTO> findTrainings();

    List<TrainingTO> findTrainingsByKeyWord(String key);

    List<TrainerTO> findTrainers(TrainingTO training);

    List<StudentTO> findStudents(TrainingTO training);

    List<TrainingTO> findTrainingsBySearchCriteria(TrainingSearchCriteriaTO criteria);

    List<TrainingTO> findTrainingsWithMostEdition();

    List<StudentTO> findStudentsWithLongestDuration();

    TrainingTO addTrainerToTraining(TrainingTO training, long trainerId) throws ParticipationInCourseException;

    TrainingTO addStudentToTraining(TrainingTO training, long studentId) throws ParticipationInCourseException, TooLargeTotalAmountException, TooMuchTrainingException;

    double sumAllCostForStudent(long studentId);

    double sumAllCostForStudentInThisYear(long studentId);

    int countAllTrainingForStudentInThisYear(long studentId);

    int sumHoursAllTrainingForTrainerInThisYear(long trainerId);

    int countAllTrainingForEmployeeInPeriod(long employeeId, Date dtFrom, Date dtTo);


}
