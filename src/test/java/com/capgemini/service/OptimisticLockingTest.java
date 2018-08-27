package com.capgemini.service;

import com.capgemini.exceptions.*;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.capgemini.service.HelpMethods.createEmployee;
import static com.capgemini.service.HelpMethods.createTraining;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class OptimisticLockingTest {

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private EmployeeService employeeService;

    @Test(expected = OptimisticLockingFailureException.class)
    public void testShouldReturnOptimisticLockingForTrainingUpdate() throws TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException, NotFoundException {
        //given

        List<String> keys = new ArrayList<>();
        keys.add("fortran");
        keys.add("oldschool");

        TrainingTO trainingTO = createTraining("Fortran for beginners", "external","technical", 2,
                "2017-10-01", "2017-10-01", keys, 2000.0);
        //when
        TrainingTO saved = trainingService.addTraining(trainingTO);

        saved.setTitle("TEST1");
        trainingService.updateTraining(trainingService.updateTraining(saved));
        saved.setTitle("TEST2");
        trainingService.updateTraining(trainingService.updateTraining(saved));


    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void testShouldReturnOptimisticLockingForEmployeeUpdate() throws NotFoundException {
        //given
        EmployeeTO employeeTO = createEmployee("Imie", "Naziwsko", "developer");
        EmployeeTO saved = employeeService.addEmployee(employeeTO);

        //when
         saved.setFirstName("Bill");
        employeeService.updateEmployee(saved);

        saved.setLastName("Gates");
        employeeService.updateEmployee(saved);


    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void testShouldReturnOptimisticLockingForStudentUpdate() throws NotFoundException, ProblemWithAddStudent {
        //given
        EmployeeTO employeeTO = createEmployee("Imie", "Naziwsko", "developer");
        EmployeeTO saved = employeeService.addEmployee(employeeTO);
        StudentTO studentTO = employeeService.addStudent(saved, 4,null);

        //when
        studentTO.setFirstName("Bill");
        employeeService.updateStudent(studentTO);

        studentTO.setLastName("Gates");
        employeeService.updateStudent(studentTO);

    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void testShouldReturnOptimisticLockingForTrainerUpdate() throws NotFoundException, ProblemWithAddTrener {
        //given
        EmployeeTO employeeTO = createEmployee("Imie", "Naziwsko", "developer");
        EmployeeTO saved = employeeService.addEmployee(employeeTO);
        TrainerTO trainerTO = employeeService.addInternalTrainer(saved);

        //when
        trainerTO.setFirstName("Bill");
        employeeService.updateTrainer(trainerTO);

        trainerTO.setLastName("Gates");
        employeeService.updateTrainer(trainerTO);

    }
}
