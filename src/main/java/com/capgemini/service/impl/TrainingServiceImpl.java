package com.capgemini.service.impl;

import com.capgemini.dao.StudentDao;
import com.capgemini.dao.TrainerDao;
import com.capgemini.dao.TrainingDao;
import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import com.capgemini.mappers.StudentMapper;
import com.capgemini.mappers.TrainerMapper;
import com.capgemini.mappers.TrainingMapper;
import com.capgemini.service.TrainingService;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private TrainerDao trainerDao;

    @Autowired
    private StudentDao studentDao;

    @Override
    public TrainingTO addTraining(TrainingTO training) {
        TrainingEntity trainingEntity = TrainingMapper.toEntity(training);
        return TrainingMapper.toTO(trainingDao.save(trainingEntity));

    }


    @Override
    public TrainingTO addTrainerToTraining(TrainingTO training, TrainerTO trainer) {
        TrainerEntity trainerEntity = TrainerMapper.toEntity(trainer);

        if(trainerEntity.getId() != null) {
            TrainingEntity trainingEntity = trainingDao.findOne(training.getId());
            trainingEntity.getTrainers().add(trainerEntity);
            return TrainingMapper.toTO(trainingDao.save(trainingEntity));
        }
        return training;
    }

    @Override
    public TrainingTO addStudentToTraining(TrainingTO training,StudentTO student) {
        StudentEntity studentEntity = StudentMapper.toEntity(student);

        if(studentEntity.getId() != null) {
            TrainingEntity trainingEntity = trainingDao.findOne(training.getId());
            trainingEntity.getStudents().add(studentEntity);
            return TrainingMapper.toTO(trainingDao.save(trainingEntity));
        }
        return training;
    }
}
