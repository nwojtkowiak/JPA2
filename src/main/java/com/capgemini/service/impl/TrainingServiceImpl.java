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
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(readOnly = false)
    public TrainingTO addTraining(TrainingTO training) {
        TrainingEntity trainingEntity = TrainingMapper.toEntity(training);
        return TrainingMapper.toTO(trainingDao.save(trainingEntity));

    }

    @Override
    @Transactional(readOnly = false)
    public TrainingTO updateTraining(TrainingTO training) {
        TrainingEntity trainingEntity = TrainingMapper.toEntity(training);
        trainingEntity = trainingDao.save(trainingEntity);

        return TrainingMapper.toTO(trainingEntity);

    }

    @Override
    public TrainingTO findTraining(long id) {
        TrainingEntity trainingEntity = trainingDao.findOne(id);

        return TrainingMapper.toTO(trainingEntity);
    }

    @Override
    public List<TrainingTO> findTrainings() {
       return TrainingMapper.map2TOs(trainingDao.findAll() );
    }

    @Override
    public List<TrainerTO> findTrainers(TrainingTO training) {
        TrainingEntity trainingEntity = trainingDao.findOne(training.getId());
        return TrainerMapper.map2TOs(trainingEntity.getTrainers());
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
