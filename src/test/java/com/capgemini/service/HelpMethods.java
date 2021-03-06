package com.capgemini.service;

import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;

import java.util.List;
import java.util.stream.Collectors;

public class HelpMethods {

    public static EmployeeTO createEmployee(String firstName, String lastName, String position) {

        return new EmployeeTO.EmployeeTOBuilder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withPosition(position).build();
    }

    public static StudentTO createStudent(String firstName, String lastName, String position) {

        return new StudentTO.StudentTOBuilder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withPosition(position).build();
    }

    public static TrainerTO createTrainer(String firstName, String lastName, String position, String companyName) {

        return new TrainerTO.TrainerTOBuilder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withPosition(position)
                .withCompanyName(companyName)
                .build();
    }

    public static TrainingTO createTraining(String title, String type, String kind, int duration,
                                            String dtFrom, String dtTo, List<String> keys, double amount) {

        return new TrainingTO.TrainingTOBuilder()
                .withTitle(title)
                .withType(type)
                .withKind(kind)
                .withDuration(duration)
                .withDateFrom(dtFrom)
                .withDateTo(dtTo)
                .withKeyWords(keys)
                .withAmount(amount)
                .build();
    }
    public static TrainingTO createTraining(String title, String type, String kind, int duration,
                                            String dtFrom, String dtTo, List<String> keys, double amount,
                                            List<TrainerTO> trainers, List<StudentTO> students) {
        List<Long> trainersId = trainers.stream().map(p -> p.getId()).collect(Collectors.toList());
        List<Long> studentsId = students.stream().map(p -> p.getId()).collect(Collectors.toList());

        return new TrainingTO.TrainingTOBuilder()
                .withTitle(title)
                .withType(type)
                .withKind(kind)
                .withDuration(duration)
                .withDateFrom(dtFrom)
                .withDateTo(dtTo)
                .withKeyWords(keys)
                .withAmount(amount)
                .withTrainers(trainersId)
                .withStudents(studentsId)
                .build();
    }
}
