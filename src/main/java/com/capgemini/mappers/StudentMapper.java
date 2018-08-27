package com.capgemini.mappers;

import com.capgemini.domain.StudentEntity;
import com.capgemini.types.StudentTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {

    public static StudentTO toTO(StudentEntity studentEntity) {
        if (studentEntity == null) {
            return null;
        }

        Long bossId = null;
        if (studentEntity.getBoss() != null) {
            bossId = studentEntity.getBoss().getId();
        }

        return new StudentTO.StudentTOBuilder()
                .withId(studentEntity.getId())
                .withFirstName(studentEntity.getFirstName())
                .withLastName(studentEntity.getLastName())
                .withPosition(studentEntity.getPosition())
                .withBoss(bossId)
                .withGrade(studentEntity.getGrade())
                .build();


    }

    public static StudentEntity toEntity(StudentTO studentTO) {
        if (studentTO == null) {
            return null;
        }

        return new StudentEntity(studentTO.getId(), studentTO.getFirstName(), studentTO.getLastName(), studentTO.getPosition(), studentTO.getGrade());

    }

    public static List<Long> map2Ids(List<StudentEntity> employeeEntities) {
        if (employeeEntities != null) {
            return employeeEntities.stream().map(StudentEntity::getId).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static List<StudentTO> map2TOs(List<StudentEntity> studentEntities) {
        if (studentEntities != null) {
            return studentEntities.stream().map(StudentMapper::toTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }


}
