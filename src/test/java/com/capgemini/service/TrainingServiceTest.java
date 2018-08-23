package com.capgemini.service;

import com.capgemini.exceptions.ParticipationInCourseException;
import com.capgemini.exceptions.ProblemWithAddStudent;
import com.capgemini.exceptions.ProblemWithAddTrener;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.capgemini.service.HelpMethods.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class TrainingServiceTest {

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private EmployeeService employeeService;

    private TrainingTO initTrainingTO;

    @Before
    @Transactional
    public void init(){

        List<String> keys = new ArrayList<>();
        keys.add("spring");
        keys.add("rest");
        keys.add("java");

         initTrainingTO = createTraining("Spring for beginners", "internal","tachnical", 30,
                "2018-12-01", "2018-12-10", keys, 2000.0);

        initTrainingTO = trainingService.addTraining(initTrainingTO);

        EmployeeTO employeeTO = createEmployee("Test", "Testowy", "manager");
        employeeService.addEmployee(employeeTO);

    }

    @Test
    @Transactional
    public void testshouldReturnSize1AfterAddTraining(){
        //given
        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO trainingTO = createTraining("SQL for beginners", "internal","tachnical", 2,
                "2018-10-01", "2018-10-01", keys, 2000.0);
        //when
        TrainingTO saved = trainingService.addTraining(trainingTO);
        List<TrainingTO> finds = trainingService.findTrainings();
        //then
        Long id = new Long(2);
        assertNotNull(saved);
        assertEquals(2,finds.size());
    }

    @Test(expected = OptimisticLockingFailureException.class)
    @Transactional
    public void testshouldReturnOptimisticLocking(){
        //given
        //TrainingTO trainingTO = trainingService.findTraining(1);

        TrainingTO t1 = trainingService.findTraining(1L);
        TrainingTO t2 = trainingService.findTraining(1L);
        //EmployeeTO e1 = employeeService.findEmployee(1L);
        //.setFirstName("Test2");
        t1.setTitle("TEST1");
        t2.setTitle("TEST2");
        //EmployeeTO e2 = employeeService.findEmployee(1L);
        //e2.setFirstName("Test2");



        // when
        trainingService.updateTraining(t1);
        //e1 = employeeService.updateEmployee(e1);

        t2 = trainingService.updateTraining(t2);
       // e2 = employeeService.updateEmployee(e2);


        //when
        /*initTrainingTO.setTitle("SPRING for begineers - Oracle");
        trainingService.updateTraining(initTrainingTO);
        initTrainingTO.setType("external");
        trainingService.updateTraining(initTrainingTO);*/

        //then
        fail();


    }

    @Test
    @Transactional
    public void testshouldReturnSize1Trainer() throws ParticipationInCourseException, ProblemWithAddTrener {
        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Testowa", "programmer");
        employeeTO = employeeService.addEmployee(employeeTO);
        TrainerTO trainerTO = employeeService.addInternalTrainer(employeeTO);

        //when
        trainingService.addTrainerToTraining(initTrainingTO,trainerTO);


        //then
        List<TrainerTO> trainers = trainingService.findTrainers(initTrainingTO);

        assertNotNull(trainers);
        assertEquals(1,trainers.size());
    }

    @Test
    @Transactional
    public void testshouldReturn1TrenerAnd3Students() throws ParticipationInCourseException, ProblemWithAddTrener, ProblemWithAddStudent {

        //given
        EmployeeTO employeeTO1 = createEmployee("Ania", "Treningowa1", "manager");
        employeeTO1 = employeeService.addEmployee(employeeTO1);

        TrainerTO trainerTO = employeeService.addInternalTrainer(employeeTO1);
        initTrainingTO = trainingService.addTrainerToTraining(initTrainingTO,trainerTO);

        EmployeeTO employeeTO2 = createEmployee("Ania", "Studentowa", "programmer");
        employeeTO2 = employeeService.addEmployee(employeeTO2);
        StudentTO studentTO2 = employeeService.addStudent(employeeTO2, 1, null);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO,studentTO2);

        TrainerTO trainerTO2 = createTrainer("Ania", "Treningowa2", "manager", "COMPANY");
        trainerTO2 = employeeService.addExternalTrainer(trainerTO2);
        initTrainingTO = trainingService.addTrainerToTraining(initTrainingTO,trainerTO2);

        EmployeeTO employeeTO3 = createEmployee("Ania", "Studentowa3", "programmer");
        employeeTO3 = employeeService.addEmployee(employeeTO3);

        StudentTO studentTO3 = employeeService.addStudent(employeeTO3, 2, employeeTO2);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO,studentTO3);

        EmployeeTO employeeTO4 = createEmployee("Ania", "Studentowa4", "programmer");
        employeeTO4 = employeeService.addEmployee(employeeTO4);

        StudentTO studentTO4 = employeeService.addStudent(employeeTO4, 5, employeeTO1);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO,studentTO4);

        //when
        List<TrainerTO> trainers = trainingService.findTrainers(initTrainingTO);
        List<StudentTO> students = trainingService.findStudents(initTrainingTO);


        //then
        assertNotNull(trainers);
        assertNotNull(students);
        assertEquals(2,trainers.size());
        assertEquals(3,students.size());
    }

    @Test
    @Transactional
    public void testshouldReturn5000ForStudent1() throws ParticipationInCourseException, ProblemWithAddTrener, ProblemWithAddStudent {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Treningowa1", "manager");
        employeeTO = employeeService.addEmployee(employeeTO);

        StudentTO studentTO = employeeService.addStudent(employeeTO, 1, null);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO,studentTO);

        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO trainingTO = createTraining("SQL for beginners", "internal","tachnical", 20,
                "2018-10-01", "2018-10-01", keys, 14000.0);
        //when
        TrainingTO secondTraining = trainingService.addTraining(trainingTO);
        secondTraining = trainingService.addStudentToTraining(secondTraining,studentTO);

        //when
        Long sum = trainingService.sumAllCostForStudent(studentTO);
        Long expected = new Long(16000);

        //then
        assertNotNull(sum);
        assertEquals(expected, sum);
    }


    @Test(expected =  ParticipationInCourseException.class)
    @Transactional
    public void testshouldReturnParticipationInCourseExceptionWhenTrainer() throws ParticipationInCourseException, ProblemWithAddStudent, ProblemWithAddTrener {

        //given
        EmployeeTO employeeTO1 = createEmployee("Ania", "Testowa1", "programmer");
        employeeTO1 = employeeService.addEmployee(employeeTO1);

        StudentTO studentTO1 = employeeService.addStudent(employeeTO1, 2, null);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO,studentTO1);

        TrainerTO trainerTO = employeeService.addInternalTrainer(employeeTO1);
        initTrainingTO = trainingService.addTrainerToTraining(initTrainingTO,trainerTO);


    }

    @Test(expected =  ParticipationInCourseException.class)
    @Transactional
    public void testshouldReturnParticipationInCourseExceptionWhenStudent() throws ParticipationInCourseException, ProblemWithAddTrener, ProblemWithAddStudent {

        //given
        EmployeeTO employeeTO1 = createEmployee("Ania", "Testowa1", "programmer");
        employeeTO1 = employeeService.addEmployee(employeeTO1);

        TrainerTO trainerTO = employeeService.addInternalTrainer(employeeTO1);
        initTrainingTO = trainingService.addTrainerToTraining(initTrainingTO,trainerTO);

        StudentTO studentTO1 = employeeService.addStudent(employeeTO1, 2, null);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO,studentTO1);



    }






}
