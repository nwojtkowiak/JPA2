package com.capgemini.service;

import com.capgemini.exceptions.*;
import com.capgemini.types.*;
import com.capgemini.types.TrainingSearchCriteriaTO.TrainingSearchCriteriaTOBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.capgemini.service.HelpMethods.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    public void init() {

        List<String> keys = new ArrayList<>();
        keys.add("spring");
        keys.add("rest");
        keys.add("java");

        initTrainingTO = createTraining("Spring for beginners", "internal", "technical", 30,
                "2018-12-01", "2018-12-10", keys, 2000.0);

        initTrainingTO = trainingService.addTraining(initTrainingTO);

        EmployeeTO employeeTO = createEmployee("Test", "Testowy", "manager");
        employeeService.addEmployee(employeeTO);

    }

    @Test
    @Transactional
    public void testShouldReturnSize1AfterAddTraining() {
        //given
        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO trainingTO = createTraining("SQL for beginners", "internal", "technical", 2,
                "2018-10-01", "2018-10-01", keys, 2000.0);
        //when
        TrainingTO saved = trainingService.addTraining(trainingTO);
        List<TrainingTO> finds = trainingService.findTrainings();

        //then
        assertNotNull(saved);
        assertEquals(2, finds.size());
    }


    @Test
    @Transactional
    public void testShouldReturnSize1Trainer() throws ParticipationInCourseException, ProblemWithAddTrener {
        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Testowa", "programmer");
        employeeTO = employeeService.addEmployee(employeeTO);
        TrainerTO trainerTO = employeeService.addInternalTrainer(employeeTO);

        //when
        trainingService.addTrainerToTraining(initTrainingTO, trainerTO);

        //then
        List<TrainerTO> trainers = trainingService.findTrainers(initTrainingTO);

        assertNotNull(trainers);
        assertEquals(1, trainers.size());
    }

    @Test
    @Transactional
    public void testShouldReturn1TrenerAnd3Students() throws ParticipationInCourseException, ProblemWithAddTrener, ProblemWithAddStudent, TooLargeTotalAmountException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO1 = createEmployee("Ania", "Treningowa1", "manager");
        employeeTO1 = employeeService.addEmployee(employeeTO1);

        TrainerTO trainerTO = employeeService.addInternalTrainer(employeeTO1);
        initTrainingTO = trainingService.addTrainerToTraining(initTrainingTO, trainerTO);

        EmployeeTO employeeTO2 = createEmployee("Ania", "Studentowa", "programmer");
        employeeTO2 = employeeService.addEmployee(employeeTO2);
        StudentTO studentTO2 = employeeService.addStudent(employeeTO2, 1, null);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO, studentTO2);

        TrainerTO trainerTO2 = createTrainer("Ania", "Treningowa2", "manager", "COMPANY");
        trainerTO2 = employeeService.addExternalTrainer(trainerTO2);
        initTrainingTO = trainingService.addTrainerToTraining(initTrainingTO, trainerTO2);

        EmployeeTO employeeTO3 = createEmployee("Ania", "Studentowa3", "programmer");
        employeeTO3 = employeeService.addEmployee(employeeTO3);

        StudentTO studentTO3 = employeeService.addStudent(employeeTO3, 2, employeeTO2);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO, studentTO3);

        EmployeeTO employeeTO4 = createEmployee("Ania", "Studentowa4", "programmer");
        employeeTO4 = employeeService.addEmployee(employeeTO4);

        StudentTO studentTO4 = employeeService.addStudent(employeeTO4, 5, employeeTO1);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO, studentTO4);

        //when
        List<TrainerTO> trainers = trainingService.findTrainers(initTrainingTO);
        List<StudentTO> students = trainingService.findStudents(initTrainingTO);


        //then
        assertNotNull(trainers);
        assertNotNull(students);
        assertEquals(2, trainers.size());
        assertEquals(3, students.size());
    }

    @Test
    @Transactional
    public void testShouldReturn12000ForStudent() throws ParticipationInCourseException, ProblemWithAddTrener, ProblemWithAddStudent, TooLargeTotalAmountException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Treningowa1", "manager");
        employeeTO = employeeService.addEmployee(employeeTO);

        StudentTO studentTO = employeeService.addStudent(employeeTO, 1, null);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO, studentTO);

        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO trainingTO = createTraining("SQL for beginners", "internal", "technical", 20,
                "2017-10-01", "2017-10-01", keys, 10000.0);
        //when
        TrainingTO secondTraining = trainingService.addTraining(trainingTO);
        trainingService.addStudentToTraining(secondTraining, studentTO);

        //when
        Double sum = trainingService.sumAllCostForStudent(studentTO.getId());
        Double expected = new Double(12000.0);

        //then
        assertNotNull(sum);
        assertEquals(expected, sum, 0.01);
    }

    @Test
    @Transactional
    public void testShouldReturn2000ForStudent() throws ParticipationInCourseException, ProblemWithAddTrener, ProblemWithAddStudent, TooLargeTotalAmountException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Treningowa1", "manager");
        employeeTO = employeeService.addEmployee(employeeTO);

        StudentTO studentTO = employeeService.addStudent(employeeTO, 1, null);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO, studentTO);

        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO trainingTO = createTraining("SQL for beginners", "internal", "technical", 20,
                "2017-10-01", "2017-10-01", keys, 10000.0);
        //when
        TrainingTO secondTraining = trainingService.addTraining(trainingTO);
        trainingService.addStudentToTraining(secondTraining, studentTO);

        //when
        Double sum = trainingService.sumAllCostForStudentInThisYear(studentTO.getId());
        Double expected = new Double(2000.0);

        //then
        assertNotNull(sum);
        assertEquals(expected, sum, 0.01);
    }


    @Test(expected = TooLargeTotalAmountException.class)
    @Transactional
    public void testShouldReturnTooLargeTotalAmountException() throws ParticipationInCourseException, ProblemWithAddTrener, ProblemWithAddStudent, TooLargeTotalAmountException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Treningowa1", "manager");
        employeeTO = employeeService.addEmployee(employeeTO);

        StudentTO studentTO = employeeService.addStudent(employeeTO, 1, null);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO, studentTO);

        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO trainingTO = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-01", "2018-10-01", keys, 14000.0);
        //when
        TrainingTO secondTraining = trainingService.addTraining(trainingTO);
        trainingService.addStudentToTraining(secondTraining, studentTO);


    }

    @Test(expected = TooMuchTrainingException.class)
    @Transactional
    public void testShouldReturnTooMuchTrainingException() throws ParticipationInCourseException, ProblemWithAddTrener, ProblemWithAddStudent, TooLargeTotalAmountException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Treningowa1", "manager");
        employeeTO = employeeService.addEmployee(employeeTO);

        StudentTO studentTO = employeeService.addStudent(employeeTO, 1, null);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO, studentTO);

        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO trainingTO2 = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-01", "2018-10-01", keys, 2000.0);
        TrainingTO secondTraining = trainingService.addTraining(trainingTO2);
        trainingService.addStudentToTraining(secondTraining, studentTO);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO trainingTO3 = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-01", "2018-10-01", keys, 2000.0);
        TrainingTO thirdTraining = trainingService.addTraining(trainingTO3);
        trainingService.addStudentToTraining(thirdTraining, studentTO);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO trainingTO4 = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-01", "2018-10-01", keys, 2000.0);
        TrainingTO fourthTraining = trainingService.addTraining(trainingTO4);

        //when

        trainingService.addStudentToTraining(fourthTraining, studentTO);


    }


    @Test(expected = ParticipationInCourseException.class)
    @Transactional
    public void testShouldReturnParticipationInCourseExceptionWhenTrainer() throws ParticipationInCourseException, ProblemWithAddStudent, ProblemWithAddTrener, TooLargeTotalAmountException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO1 = createEmployee("Ania", "Testowa1", "programmer");
        employeeTO1 = employeeService.addEmployee(employeeTO1);

        StudentTO studentTO1 = employeeService.addStudent(employeeTO1, 2, null);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO, studentTO1);

        TrainerTO trainerTO = employeeService.addInternalTrainer(employeeTO1);
        initTrainingTO = trainingService.addTrainerToTraining(initTrainingTO, trainerTO);


    }

    @Test(expected = ParticipationInCourseException.class)
    @Transactional
    public void testShouldReturnParticipationInCourseExceptionWhenStudent() throws ParticipationInCourseException, ProblemWithAddTrener, ProblemWithAddStudent, TooLargeTotalAmountException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO1 = createEmployee("Ania", "Testowa1", "programmer");
        employeeTO1 = employeeService.addEmployee(employeeTO1);

        TrainerTO trainerTO = employeeService.addInternalTrainer(employeeTO1);
        initTrainingTO = trainingService.addTrainerToTraining(initTrainingTO, trainerTO);

        StudentTO studentTO1 = employeeService.addStudent(employeeTO1, 2, null);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO, studentTO1);


    }

    @Test
    @Transactional
    public void testNotShouldReturnTooMuchTrainingException() throws ParticipationInCourseException, ProblemWithAddTrener, ProblemWithAddStudent, TooLargeTotalAmountException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Treningowa1", "manager");
        employeeTO = employeeService.addEmployee(employeeTO);

        StudentTO studentTO = employeeService.addStudent(employeeTO, 4, null);
        initTrainingTO = trainingService.addStudentToTraining(initTrainingTO, studentTO);

        //when

        trainingService.addStudentToTraining(initTrainingTO, studentTO);


    }

    @Test(expected = TooLargeTotalAmountException.class)
    @Transactional
    public void testShouldReturnTooLargeTotalAmountExceptionForGrad4() throws ParticipationInCourseException, ProblemWithAddStudent, TooLargeTotalAmountException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Treningowa1", "manager");
        employeeTO = employeeService.addEmployee(employeeTO);
        StudentTO studentTO = employeeService.addStudent(employeeTO, 4, null);

        List<String> keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO trainingTO = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-01", "2018-10-01", keys, 55100.0);
        trainingTO = trainingService.addTraining(trainingTO);


        //when
        trainingService.addStudentToTraining(trainingTO, studentTO);


    }

    @Test
    @Transactional
    public void testShouldListTrainingWithSql() throws ParticipationInCourseException, ProblemWithAddTrener, ProblemWithAddStudent, TooLargeTotalAmountException, TooMuchTrainingException {

        //given
        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO trainingTO1 = createTraining("SQL for beginners", "internal", "technical", 20,
                "2019-10-01", "2019-10-01", keys, 10000.0);

        trainingService.addTraining(trainingTO1);

        keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO trainingTO2 = createTraining("SQL for Experts", "internal", "technical", 20,
                "2019-10-01", "2019-10-01", keys, 20000.0);
        trainingService.addTraining(trainingTO2);


        //when
        List<TrainingTO> finds = trainingService.findTrainingsByKeyWord("sql");


        //then
        assertNotNull(finds);
        assertEquals(2, finds.size());
    }


    @Test
    @Transactional
    public void testShouldReturn40hours() throws ProblemWithAddTrener, ParticipationInCourseException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Treningowa1", "manager");
        employeeTO = employeeService.addEmployee(employeeTO);

        TrainerTO trainerTO = employeeService.addInternalTrainer(employeeTO);

        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO secondTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2017-10-01", "2017-10-01", keys, 2000.0);
        secondTraining = trainingService.addTraining(secondTraining);
        trainingService.addTrainerToTraining(secondTraining, trainerTO);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO thirdTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-01", "2018-10-01", keys, 2000.0);
        thirdTraining = trainingService.addTraining(thirdTraining);
        trainingService.addTrainerToTraining(thirdTraining, trainerTO);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fourthTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-01", "2018-10-01", keys, 2000.0);
        fourthTraining = trainingService.addTraining(fourthTraining);
        trainingService.addTrainerToTraining(fourthTraining, trainerTO);

        //when
        int countHours = trainingService.sumHoursAllTrainingForTrainerInThisYear(trainerTO.getId());

        //then
        assertEquals(40, countHours);
    }

    @Test
    @Transactional
    public void testShouldReturn3TrainingForEmployee() throws ProblemWithAddTrener, ParticipationInCourseException, ProblemWithAddStudent, TooLargeTotalAmountException, TooMuchTrainingException {

        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Treningowa1", "manager");
        employeeTO = employeeService.addEmployee(employeeTO);

        TrainerTO trainerTO = employeeService.addInternalTrainer(employeeTO);
        StudentTO studentTO = employeeService.addStudent(employeeTO, 4, null);

        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO secondTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-10", "2018-10-20", keys, 2000.0);
        secondTraining = trainingService.addTraining(secondTraining);
        trainingService.addTrainerToTraining(secondTraining, trainerTO);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO thirdTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-01", "2018-10-10", keys, 2000.0);
        thirdTraining = trainingService.addTraining(thirdTraining);
        trainingService.addStudentToTraining(thirdTraining, studentTO);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fourthTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-20", "2018-10-22", keys, 2000.0);
        fourthTraining = trainingService.addTraining(fourthTraining);
        trainingService.addTrainerToTraining(fourthTraining, trainerTO);

        //when
        int countTrainings = trainingService.countAllTrainingForEmployeeInPeriod(employeeTO.getId(),
                Date.valueOf("2018-10-01"), Date.valueOf("2018-10-31"));

        //then
        assertEquals(3, countTrainings);
    }

    @Test
    @Transactional
    public void testShouldReturn4TrainingWithEmptySearchCriteria() {

        //give
        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO secondTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-10", "2018-10-20", keys, 2000.0);
        trainingService.addTraining(secondTraining);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO thirdTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-01", "2018-10-10", keys, 2000.0);
        trainingService.addTraining(thirdTraining);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fourthTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-20", "2018-10-22", keys, 2000.0);
        trainingService.addTraining(fourthTraining);

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTOBuilder()
                .build();

        //when
        List<TrainingTO> trainings = trainingService.findTrainingsBySearchCriteria(criteria);

        //then
        assertNotNull(trainings);
        assertEquals(4, trainings.size());
    }

    @Test
    @Transactional
    public void testShouldReturn4TrainingWithTitleSearchCriteria() {

        //give
        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO secondTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-10", "2018-10-20", keys, 2000.0);
        trainingService.addTraining(secondTraining);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO thirdTraining = createTraining("html for beginners", "internal", "technical", 20,
                "2018-10-01", "2018-10-10", keys, 2000.0);
        trainingService.addTraining(thirdTraining);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fourthTraining = createTraining("C++ for beginners", "internal", "technical", 20,
                "2018-10-20", "2018-10-22", keys, 2000.0);
        trainingService.addTraining(fourthTraining);

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTOBuilder()
                .withTitle("for beginners")
                .build();

        //when
        List<TrainingTO> trainings = trainingService.findTrainingsBySearchCriteria(criteria);

        //then
        assertNotNull(trainings);
        assertEquals(4, trainings.size());
    }

    @Test
    @Transactional
    public void testShouldReturn2TrainingWithTypeSearchCriteria() {

        //give
        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO secondTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-10", "2018-10-20", keys, 2000.0);
        trainingService.addTraining(secondTraining);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO thirdTraining = createTraining("html for beginners", "external", "technical", 20,
                "2018-10-01", "2018-10-10", keys, 2000.0);
        trainingService.addTraining(thirdTraining);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fourthTraining = createTraining("C++ for beginners", "external", "technical", 20,
                "2018-10-20", "2018-10-22", keys, 2000.0);
        trainingService.addTraining(fourthTraining);

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTOBuilder()
                .withTitle("for beginners")
                .withType("internal")
                .build();
        //when
        List<TrainingTO> trainings = trainingService.findTrainingsBySearchCriteria(criteria);

        //then
        assertNotNull(trainings);
        assertEquals(2, trainings.size());
    }

    @Test
    @Transactional
    public void testShouldReturn1TrainingWithKindSearchCriteria() {

        //give
        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO secondTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-10", "2018-10-20", keys, 2000.0);
        trainingService.addTraining(secondTraining);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO thirdTraining = createTraining("html for beginners", "external", "technical", 20,
                "2018-10-01", "2018-10-10", keys, 2000.0);
        trainingService.addTraining(thirdTraining);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fourthTraining = createTraining("C++ for beginners", "external", "technical", 20,
                "2018-10-20", "2018-10-22", keys, 2000.0);
        trainingService.addTraining(fourthTraining);

        keys = new ArrayList<>();
        keys.add("speech");

        TrainingTO fifthTraining = createTraining("Speech for beginners", "external", "soft", 20,
                "2019-10-20", "2019-10-22", keys, 5000.0);
        trainingService.addTraining(fifthTraining);

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTOBuilder()
                .withKind("soft")
                .build();
        //when
        List<TrainingTO> trainings = trainingService.findTrainingsBySearchCriteria(criteria);

        //then
        assertNotNull(trainings);
        assertEquals(1, trainings.size());
    }

    @Test
    @Transactional
    public void testShouldReturn1TrainingWithDateSearchCriteria() {

        //give
        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO secondTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-10", "2018-10-20", keys, 2000.0);
        trainingService.addTraining(secondTraining);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO thirdTraining = createTraining("html for beginners", "external", "technical", 20,
                "2018-10-01", "2018-10-10", keys, 2000.0);
        trainingService.addTraining(thirdTraining);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fourthTraining = createTraining("C++ for beginners", "external", "technical", 20,
                "2018-10-20", "2018-10-22", keys, 2000.0);
        trainingService.addTraining(fourthTraining);

        keys = new ArrayList<>();
        keys.add("speech");

        TrainingTO fifthTraining = createTraining("Speech for beginners", "external", "soft", 20,
                "2019-10-20", "2019-10-22", keys, 5000.0);
        trainingService.addTraining(fifthTraining);

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTOBuilder()
                .withDate(Date.valueOf("2019-10-21"))
                .build();

        //when
        List<TrainingTO> trainings = trainingService.findTrainingsBySearchCriteria(criteria);

        //then
        assertNotNull(trainings);
    }

    @Test
    @Transactional
    public void testShouldReturn2TrainingWithAmountFromSearchCriteria() {

        //give
        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO secondTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-10", "2018-10-20", keys, 2000.0);
        trainingService.addTraining(secondTraining);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO thirdTraining = createTraining("html for beginners", "external", "technical", 20,
                "2018-10-01", "2018-10-10", keys, 2000.0);
        trainingService.addTraining(thirdTraining);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fourthTraining = createTraining("C++ for beginners", "external", "technical", 20,
                "2018-10-20", "2018-10-22", keys, 7000.0);
        trainingService.addTraining(fourthTraining);

        keys = new ArrayList<>();
        keys.add("speech");

        TrainingTO fifthTraining = createTraining("Speech for beginners", "external", "soft", 20,
                "2019-10-20", "2019-10-22", keys, 5000.0);
        trainingService.addTraining(fifthTraining);

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTOBuilder()
                .withAmountFrom(5000.0)
                .build();

        //when
        List<TrainingTO> trainings = trainingService.findTrainingsBySearchCriteria(criteria);

        //then
        assertNotNull(trainings);
        assertEquals(2, trainings.size());

    }

    @Test
    @Transactional
    public void testShouldReturn1TrainingWithAmountFromAdnToSearchCriteria() {

        //give
        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO secondTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-10", "2018-10-20", keys, 2000.0);
        trainingService.addTraining(secondTraining);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO thirdTraining = createTraining("html for beginners", "external", "technical", 20,
                "2018-10-01", "2018-10-10", keys, 2000.0);
        trainingService.addTraining(thirdTraining);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fourthTraining = createTraining("C++ for beginners", "external", "technical", 20,
                "2018-10-20", "2018-10-22", keys, 7000.0);
        trainingService.addTraining(fourthTraining);

        keys = new ArrayList<>();
        keys.add("speech");

        TrainingTO fifthTraining = createTraining("Speech for beginners", "external", "soft", 20,
                "2019-10-20", "2019-10-22", keys, 5000.0);
        trainingService.addTraining(fifthTraining);

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTOBuilder()

                .withAmountFrom(5000.0)
                .withAmountTo(6000.0)
                .build();

        //when
        List<TrainingTO> trainings = trainingService.findTrainingsBySearchCriteria(criteria);

        //then
        assertNotNull(trainings);
        assertEquals(1, trainings.size());
    }


    @Test
    @Transactional
    public void testShouldReturn2TrainingWithAllSearchCriteria() {

        //give
        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO secondTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-10", "2018-10-20", keys, 3000.0);
        trainingService.addTraining(secondTraining);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO thirdTraining = createTraining("html for beginners", "external", "technical", 20,
                "2018-10-01", "2018-10-10", keys, 3000.0);
        trainingService.addTraining(thirdTraining);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fourthTraining = createTraining("C++ for beginners", "external", "technical", 20,
                "2018-10-20", "2018-10-22", keys, 2000.0);
        trainingService.addTraining(fourthTraining);

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTOBuilder()
                .withTitle("for beginners")
                .withType("internal")
                .withKind("technical")
                .withDate(Date.valueOf("2018-10-20"))
                .withAmountFrom(2000.0)
                .withAmountTo(4000.0)
                .build();


        //when
        List<TrainingTO> trainings = trainingService.findTrainingsBySearchCriteria(criteria);

        //then
        assertNotNull(trainings);
        assertEquals(1, trainings.size());


    }

    @Test
    @Transactional
    public void testShouldReturn2StudentWithLongestDuration() throws ProblemWithAddStudent, TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

        //give
        EmployeeTO employeeTO1 = createEmployee("Ania", "Treningowa1", "manager");
        employeeTO1 = employeeService.addEmployee(employeeTO1);
        StudentTO studentTO1 = employeeService.addStudent(employeeTO1, 4, null);

        EmployeeTO employeeTO2 = createEmployee("Ania", "Treningowa2", "manager");
        employeeTO2 = employeeService.addEmployee(employeeTO2);
        StudentTO studentTO2 = employeeService.addStudent(employeeTO2, 4, null);

        EmployeeTO employeeTO3 = createEmployee("Ania", "Treningowa3", "manager");
        employeeTO3 = employeeService.addEmployee(employeeTO3);
        StudentTO studentTO3 = employeeService.addStudent(employeeTO3, 4, null);

        EmployeeTO employeeTO4 = createEmployee("Ania", "Treningowa4", "manager");
        employeeTO4 = employeeService.addEmployee(employeeTO4);
        StudentTO studentTO4 = employeeService.addStudent(employeeTO4, 4, null);

        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO secondTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-10", "2018-10-20", keys, 3000.0);
        secondTraining = trainingService.addTraining(secondTraining);
        trainingService.addStudentToTraining(secondTraining, studentTO1);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO thirdTraining = createTraining("html for beginners", "external", "technical", 50,
                "2018-10-01", "2018-10-10", keys, 3000.0);
        thirdTraining = trainingService.addTraining(thirdTraining);
        trainingService.addStudentToTraining(thirdTraining, studentTO2);
        trainingService.addStudentToTraining(thirdTraining, studentTO3);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fourthTraining = createTraining("C++ for beginners", "external", "technical", 10,
                "2018-10-20", "2018-10-22", keys, 2000.0);
        fourthTraining = trainingService.addTraining(fourthTraining);
        trainingService.addStudentToTraining(fourthTraining, studentTO2);
        trainingService.addStudentToTraining(fourthTraining, studentTO3);
        trainingService.addStudentToTraining(fourthTraining, studentTO4);


        //when
        List<StudentTO> students = trainingService.findStudentsWithLongestDuration();

        //then
        assertNotNull(students);
        assertEquals(2, students.size());
        assertEquals("Treningowa2", students.get(0).getLastName());
        assertEquals("Treningowa3", students.get(1).getLastName());


    }

    @Test
    @Transactional
    public void testShouldReturn1TrainingWithMostEdition() {

        //give
        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO secondTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-10", "2018-10-20", keys, 3000.0);
        secondTraining = trainingService.addTraining(secondTraining);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO thirdTraining = createTraining("SQL for beginners", "external", "technical", 50,
                "2020-10-01", "2018-10-20", keys, 3000.0);
        thirdTraining = trainingService.addTraining(thirdTraining);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fourthTraining = createTraining("C++ for beginners", "external", "technical", 10,
                "2020-10-20", "2020-10-22", keys, 2000.0);
        fourthTraining = trainingService.addTraining(fourthTraining);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO fifthTraining = createTraining("SQL for beginners", "external", "technical", 50,
                "2019-10-01", "2019-10-10", keys, 3000.0);
        fifthTraining = trainingService.addTraining(fifthTraining);

        //when
        List<TrainingTO> trainings = trainingService.findTrainingsWithMostEdition();

        //then
        assertNotNull(trainings);
        assertEquals(1, trainings.size());
        assertEquals("SQL for beginners", trainings.get(0).getTitle());
    }

    @Test
    @Transactional
    public void testShouldReturn2TrainingWithMostEdition()  {

        //give
        List<String> keys = new ArrayList<>();
        keys.add("sql");
        keys.add("oracle");

        TrainingTO secondTraining = createTraining("SQL for beginners", "internal", "technical", 20,
                "2018-10-10", "2018-10-20", keys, 3000.0);
        secondTraining = trainingService.addTraining(secondTraining);

        keys = new ArrayList<>();
        keys.add("html");
        keys.add("css");

        TrainingTO thirdTraining = createTraining("SQL for beginners", "external", "technical", 50,
                "2020-10-01", "2018-10-20", keys, 3000.0);
        thirdTraining = trainingService.addTraining(thirdTraining);



        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fourthTraining = createTraining("C++ for beginners", "external", "technical", 50,
                "2019-10-01", "2019-10-10", keys, 3000.0);
        fourthTraining = trainingService.addTraining(fourthTraining);

        keys = new ArrayList<>();
        keys.add("c++");

        TrainingTO fifthTraining = createTraining("C++ for beginners", "external", "technical", 10,
                "2020-10-20", "2020-10-22", keys, 2000.0);
        fifthTraining = trainingService.addTraining(fifthTraining);

        //when
        List<TrainingTO> trainings = trainingService.findTrainingsWithMostEdition();

        //then
        assertNotNull(trainings);
        assertEquals(2, trainings.size());
        assertEquals("SQL for beginners", trainings.get(0).getTitle());
        assertEquals(Date.valueOf("2018-10-10"), trainings.get(0).getDateFrom());
        assertEquals("C++ for beginners", trainings.get(1).getTitle());
        assertEquals(Date.valueOf("2019-10-01"), trainings.get(1).getDateFrom());


    }


}
