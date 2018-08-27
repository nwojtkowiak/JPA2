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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.capgemini.service.HelpMethods.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TrainingService trainingService;

    @Test
    @Transactional
    public void testShouldReturnNewIdAfterAddEmployee() {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Testowa", "programmer");
        employeeTO = employeeService.addEmployee(employeeTO);

        //when
        EmployeeTO findEntity = employeeService.findEmployee(employeeTO.getId());

        //then
        assertNotNull(findEntity);
        assertEquals(employeeTO.getId(), findEntity.getId());
    }

    @Test
    @Transactional
    public void testShouldReturnNullAfterDelEmployeeWithStudent() throws NotFoundException, ProblemWithAddStudent, ProblemWithAddTrener, TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Testowa", "programmer");
        employeeTO = employeeService.addEmployee(employeeTO);

        StudentTO student = employeeService.addStudent(employeeTO, 4, null);
        TrainerTO trainer = employeeService.addInternalTrainer(employeeTO);

        List<String> keys = new ArrayList<>();
        keys.add("java");

        //when
        TrainingTO training = createTraining("Spring", "internal",
                "technical", 20,
                "2020-05-06","2020-05-08",
                keys,5000.0);
        training = trainingService.addTraining(training);

        trainingService.addStudentToTraining(training,student.getId());
       // trainingService.addTrainerToTraining(training,trainer.getId());

        EmployeeTO findEmployee = employeeService.findEmployee(employeeTO.getId());
        StudentTO findStudent = employeeService.findStudent(student.getId());
        TrainerTO findTrainer = employeeService.findTrainer(trainer.getId());
        List<StudentTO> studentsForTraining = trainingService.findStudents(training);

        //then
        assertNotNull(findEmployee);
        assertNotNull(findStudent);
        assertNotNull(findTrainer);
        assertEquals(1, studentsForTraining.size());


        //when
        employeeService.delEmployee(employeeTO.getId());
         findEmployee = employeeService.findEmployee(employeeTO.getId());
         findStudent = employeeService.findStudent(student.getId());
         findTrainer = employeeService.findTrainer(trainer.getId());
        studentsForTraining = trainingService.findStudents(training);

        //then
        assertNull(findEmployee);
        assertNull(findStudent);
        assertNull(findTrainer);
        assertEquals(0, studentsForTraining.size());
    }

    @Test
    @Transactional
    public void testShouldReturnNullAfterDelEmployeeWithTrainer() throws NotFoundException, ProblemWithAddStudent, ProblemWithAddTrener, TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Testowa", "programmer");
        employeeTO = employeeService.addEmployee(employeeTO);

        StudentTO student = employeeService.addStudent(employeeTO, 4, null);
        TrainerTO trainer = employeeService.addInternalTrainer(employeeTO);

        List<String> keys = new ArrayList<>();
        keys.add("java");

        //when
        TrainingTO training = createTraining("Spring", "internal",
                "technical", 20,
                "2020-05-06","2020-05-08",
                keys,5000.0);
        training = trainingService.addTraining(training);
        trainingService.addTrainerToTraining(training,trainer.getId());

        //when
        employeeService.delEmployee(employeeTO.getId());
        EmployeeTO findEmployee = employeeService.findEmployee(employeeTO.getId());
        StudentTO findStudent = employeeService.findStudent(student.getId());
        TrainerTO findTrainer = employeeService.findTrainer(trainer.getId());
        List<TrainerTO> trainersForTraining = trainingService.findTrainers(training);

        //then
        assertNull(findEmployee);
        assertNull(findStudent);
        assertNull(findTrainer);
        assertEquals(0, trainersForTraining.size());
    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void testShouldReturnNotFoundExceptionAfterDelEmployeeNotExist() throws NotFoundException {
        //given
        Long id = 55L;

        //when
        employeeService.delEmployee(id);

    }

    @Test
    @Transactional
    public void testShouldReturnNullWhenFindNotExist() {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Testowa", "programmer");
        employeeTO = employeeService.addEmployee(employeeTO);

        //when
        EmployeeTO findEmployee = employeeService.findEmployee(employeeTO.getId()+1);

        //then
        assertNull(findEmployee);
    }


    @Test
    @Transactional
    public void testShouldReturnEmployeeWithNewNameAfterUpdate() throws NotFoundException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Testowa", "programmer");
        employeeTO = employeeService.addEmployee(employeeTO);

        //when
        EmployeeTO findEmployee = employeeService.findEmployee(employeeTO.getId());
        findEmployee.setFirstName("TEST");

        EmployeeTO updatedEmployee = employeeService.updateEmployee(findEmployee);

        //then
        assertNotNull(updatedEmployee);
        assertEquals("TEST", updatedEmployee.getFirstName());
    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void testShouldReturnNotFoundExceptionAfterUpdateNotExist() throws NotFoundException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Testowa", "programmer");

        //when
        employeeTO.setFirstName("TEST");
        employeeService.updateEmployee(employeeTO);

    }


    @Test
    public void testShouldReturnUpdatedForStudentAfterUpdate() throws NotFoundException, ProblemWithAddStudent {
        //given
        EmployeeTO employeeTO = createEmployee("Imie", "Naziwsko", "developer");
        EmployeeTO saved = employeeService.addEmployee(employeeTO);
        StudentTO studentTO = employeeService.addStudent(saved, 4,null);

        //when
        studentTO.setFirstName("Bill");
        employeeService.updateStudent(studentTO);

        //then
        StudentTO updated = employeeService.findStudent(studentTO.getId());
        assertEquals("Bill", updated.getFirstName());

    }

    @Test
    public void testShouldReturnUpdatedForTrainerAfterUpdate() throws NotFoundException, ProblemWithAddTrener {
        //given
        EmployeeTO employeeTO = createEmployee("Imie", "Naziwsko", "developer");
        EmployeeTO saved = employeeService.addEmployee(employeeTO);
        TrainerTO trainerTO = employeeService.addInternalTrainer(saved);

        //when
        trainerTO.setFirstName("Bill");
        employeeService.updateTrainer(trainerTO);

        //then
        TrainerTO updated = employeeService.findTrainer(trainerTO.getId());
        assertEquals("Bill", updated.getFirstName());


    }

    @Test(expected = NotFoundException.class)
    public void testShouldReturnNotFoundExceptiondForStudentAfterUpdateNotExist() throws NotFoundException, ProblemWithAddStudent {
        //given
        EmployeeTO employeeTO = createEmployee("Imie", "Naziwsko", "developer");
        EmployeeTO saved = employeeService.addEmployee(employeeTO);
        StudentTO studentTO = createStudent("Imie", "Nazwisko", "manager");

        //when
        //when
        studentTO.setFirstName("Bill");
        employeeService.updateStudent(studentTO);

    }

    @Test(expected = NotFoundException.class)
    public void testShouldReturnNotFoundExceptionForTrainerAfterUpdateNotExist() throws NotFoundException, ProblemWithAddTrener {
        //given
        EmployeeTO employeeTO = createEmployee("Imie", "Naziwsko", "developer");
        EmployeeTO saved = employeeService.addEmployee(employeeTO);
        TrainerTO trainerTO = createTrainer("Imie", "Nazwisko","manager", "");

        //when
        trainerTO.setFirstName("Bill");
        employeeService.updateTrainer(trainerTO);


    }

    @Test
    @Transactional
    public void testShouldReturnNullAfterDelStudent() throws NotFoundException, ProblemWithAddStudent, ProblemWithAddTrener, TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Testowa", "programmer");
        employeeTO = employeeService.addEmployee(employeeTO);

        StudentTO student = employeeService.addStudent(employeeTO, 4, null);

        //when
        employeeService.delStudent(student.getId());

        //then
        StudentTO findStudent = employeeService.findStudent(student.getId());
        assertNull(findStudent);

    }

    @Test
    @Transactional
    public void testShouldReturnNullAfterDelTrainer() throws NotFoundException, ProblemWithAddStudent, ProblemWithAddTrener, TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Testowa", "programmer");
        employeeTO = employeeService.addEmployee(employeeTO);

        TrainerTO trainer = employeeService.addInternalTrainer(employeeTO);

        //when
        employeeService.delTrainer(trainer.getId());

        //then
        TrainerTO findTrainer = employeeService.findTrainer(trainer.getId());
        assertNull(findTrainer);
    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void testShouldReturnNotFoundExceptionAfterDelNotExistStudent() throws NotFoundException, ProblemWithAddStudent, ProblemWithAddTrener, TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

        //given
        Long id = 4L;

        //when
        employeeService.delStudent(id);

    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void testShouldReturnNotFoundExceptionAfterDelNotExistTrainer() throws NotFoundException, ProblemWithAddStudent, ProblemWithAddTrener, TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

        //given
        Long id = 4L;

        //when
        employeeService.delTrainer(id);

    }
}
