package com.capgemini.service;

import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;

import java.util.List;

public interface EmployeeService {

    EmployeeTO addEmployee(EmployeeTO employee);

    EmployeeTO updateEmployee(EmployeeTO employee);

    TrainerTO addTrainer(EmployeeTO employee,TrainerTO trainer);

    StudentTO addStudent(EmployeeTO employee,StudentTO student);

    EmployeeTO findEmployee(long id);

    boolean compareTrainersAndStudents(List<TrainerEntity> trainers, List<StudentEntity> students);
}
