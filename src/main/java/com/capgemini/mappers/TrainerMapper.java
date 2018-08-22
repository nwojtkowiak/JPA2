package com.capgemini.mappers;

import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainerMapper {

    public static TrainerTO toTO(TrainerEntity trainerEntity) {
        if (trainerEntity == null) {
            return null;
        }


        return new TrainerTO.TrainerTOBuilder()
                .withId(trainerEntity.getId())
                .withFirstName(trainerEntity.getFirstName())
                .withLastName(trainerEntity.getLastName())
                .withCompanyName(trainerEntity.getCompanyName())
                .build();


    }

    public static TrainerEntity toEntity(TrainerTO trainerTO) {
        if (trainerTO == null) {
            return null;
        }

        return new TrainerEntity(trainerTO.getId(), trainerTO.getFirstName(), trainerTO.getLastName(),
                trainerTO.getPosition(), trainerTO.getCompanyName());

    }

    public static List<Long> map2TOs(List<TrainerEntity> trainerEntities) {
        if (trainerEntities != null) {
            return trainerEntities.stream().map(TrainerEntity::getId).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}