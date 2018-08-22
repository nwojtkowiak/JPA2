package com.capgemini.mappers;

import com.capgemini.domain.TrainingEntity;
import com.capgemini.types.TrainingTO;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainingMapper {

    public static TrainingTO toTO(TrainingEntity trainingEntity) {
        if (trainingEntity == null) {
            return null;
        }


        return new TrainingTO.TrainingTOBuilder()
                .withId(trainingEntity.getId())
                .withTitle(trainingEntity.getTitle())
                .withType(trainingEntity.getType())
                .withKind(trainingEntity.getKind())
                .withDuration(trainingEntity.getDuration())
                .withDuration(trainingEntity.getDuration())
                .withDateFrom(trainingEntity.getDateFrom())
                .withDateTo(trainingEntity.getDateTo())
                .withAmount(trainingEntity.getAmount())
                .withStudents(StudentMapper.map2TOs(trainingEntity.getStudents()))
                .withTrainers(TrainerMapper.map2TOs(trainingEntity.getTrainers()))
                .build();


    }

    public static TrainingEntity toEntity(TrainingTO trainingTO) {
        if (trainingTO == null) {
            return null;
        }

        return new TrainingEntity(trainingTO.getId(),
                trainingTO.getTitle(),
                trainingTO.getType(),
                trainingTO.getKind(),
                trainingTO.getDateFrom(),
                trainingTO.getDateTo(),
                trainingTO.getDuration(),
                trainingTO.getKeyWords(),
                trainingTO.getAmount());

    }

    public static List<TrainingTO> map2TOs(List<TrainingEntity> trainingEntityList) {
        if (trainingEntityList != null) {
            return trainingEntityList.stream().map(TrainingMapper::toTO).collect(Collectors.toList());
        }
        return new LinkedList<>();
    }
}