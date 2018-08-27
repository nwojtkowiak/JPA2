package com.capgemini.service;

import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.exceptions.ProblemWithAddStudent;
import com.capgemini.exceptions.ProblemWithAddTrener;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import javassist.NotFoundException;

import java.util.List;

public interface EmployeeService {

    EmployeeTO addEmployee(EmployeeTO employee);

    void delEmployee(long id) throws NotFoundException;

    void delStudent(long id) throws NotFoundException;

    void delTrainer(long id) throws NotFoundException;

    EmployeeTO updateEmployee(EmployeeTO employee) throws NotFoundException;

    StudentTO updateStudent(StudentTO student) throws NotFoundException;

    TrainerTO updateTrainer(TrainerTO trainerTO) throws NotFoundException;

    TrainerTO addInternalTrainer(EmployeeTO employee) throws ProblemWithAddTrener;

    TrainerTO addExternalTrainer(TrainerTO trainer) throws ProblemWithAddTrener;

    StudentTO addStudent(EmployeeTO employee, int grade, EmployeeTO boss) throws ProblemWithAddStudent;

    EmployeeTO findEmployee(long id);

    StudentTO findStudent(long studentId);

    TrainerTO findTrainer(long trainerId);

    EmployeeTO findEmployeeByStudent(long studentId);

    EmployeeTO findEmployeeByTrainer(long trainerId);

    boolean compareTrainersAndStudents(List<TrainerEntity> trainers, List<StudentEntity> students);
}
