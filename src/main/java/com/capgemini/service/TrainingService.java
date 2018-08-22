package com.capgemini.service;

import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;

public interface TrainingService {

    TrainingTO addTraining(TrainingTO training);

    void delTraining(TrainingTO training);

    TrainingTO addTrainer(TrainerTO trainer);

    TrainerTO addStudent(StudentTO student);
}
