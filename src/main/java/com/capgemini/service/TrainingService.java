package com.capgemini.service;

import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;

public interface TrainingService {

    TrainingTO addTraining(TrainingTO training);

    TrainingTO addTrainerToTraining(TrainingTO training,TrainerTO trainer);

    TrainingTO addStudentToTraining(TrainingTO training, StudentTO student);
}
