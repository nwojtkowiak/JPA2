package com.capgemini.service;

import com.capgemini.exceptions.ParticipationInCourseException;
import com.capgemini.exceptions.TooLargeTotalAmountException;
import com.capgemini.exceptions.TooMuchTrainingException;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.List;

public interface TrainingService {

    TrainingTO addTraining(TrainingTO training);

    TrainingTO updateTraining(TrainingTO training) throws OptimisticLockingFailureException;

    TrainingTO findTraining(long id);

    List<TrainingTO> findTrainings();

    List<TrainerTO> findTrainers(TrainingTO training);

    List<StudentTO> findStudents(TrainingTO training);

    TrainingTO addTrainerToTraining(TrainingTO training,TrainerTO trainer) throws ParticipationInCourseException;

    TrainingTO addStudentToTraining(TrainingTO training, StudentTO student) throws ParticipationInCourseException, TooLargeTotalAmountException, TooMuchTrainingException;

    Double sumAllCostForStudent(StudentTO student);
}
