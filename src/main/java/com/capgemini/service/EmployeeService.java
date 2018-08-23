package com.capgemini.service;

import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.exceptions.ProblemWithAddStudent;
import com.capgemini.exceptions.ProblemWithAddTrener;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;

import java.util.List;

public interface EmployeeService {

    EmployeeTO addEmployee(EmployeeTO employee);

    EmployeeTO updateEmployee(EmployeeTO employee);

    TrainerTO addInternalTrainer(EmployeeTO employee) throws ProblemWithAddTrener;

    TrainerTO addExternalTrainer(TrainerTO trainer) throws ProblemWithAddTrener;

    StudentTO addStudent(EmployeeTO employee, int grade, EmployeeTO boss) throws ProblemWithAddStudent;

    EmployeeTO findEmployee(long id);

    EmployeeEntity findEmployeeByStudent(long student_id);

    boolean compareTrainersAndStudents(List<TrainerEntity> trainers, List<StudentEntity> students);
}
