package com.capgemini.mappers;

import com.capgemini.domain.EmployeeEntity;
import com.capgemini.types.*;
import com.capgemini.types.EmployeeTO.EmployeeTOBuilder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class EmployeeMapper {
    public static EmployeeTO toTO(EmployeeEntity employeeEntity) {
        if (employeeEntity == null) {
            return null;
        }

        Long studentId = null;
        Long trainerId = null;
        if(employeeEntity.getStudent() != null){
            studentId = employeeEntity.getStudent().getId();
        }
        if(employeeEntity.getTrainer() != null){
            trainerId = employeeEntity.getTrainer().getId();
        }

        return new EmployeeTOBuilder().withFirstName(employeeEntity.getFirstName())
                .withId(employeeEntity.getId())
                .withLastName(employeeEntity.getLastName())
                .withPosition(employeeEntity.getPosition())
                .withStudent(studentId)
                .withTrener(trainerId)
                .build();


    }

    public static EmployeeEntity toEntity(EmployeeTO employeeTO) {
        if (employeeTO == null) {
            return null;
        }

        return new EmployeeEntity(employeeTO.getId(), employeeTO.getFirstName(), employeeTO.getLastName(), employeeTO.getPosition());

    }

    public static List<EmployeeTO> map2TOs(Collection<EmployeeEntity> employeeEntities) {
        if (employeeEntities != null) {
            return employeeEntities.stream().map(EmployeeMapper::toTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
