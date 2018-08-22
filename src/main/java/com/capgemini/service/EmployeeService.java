package com.capgemini.service;

import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;

public interface EmployeeService {

    EmployeeTO addEmployee(EmployeeTO employee);

    TrainerTO addTrainer(EmployeeTO employee,TrainerTO trainer);

    EmployeeTO addStudent(EmployeeTO employee,StudentTO student);
}
