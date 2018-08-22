package com.capgemini.service;

import com.capgemini.types.EmployeeTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.capgemini.service.HelpMethods.createEmployee;
import static com.capgemini.service.HelpMethods.createTrainer;
import static com.capgemini.service.HelpMethods.createTraining;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class TrainingServiceTest {

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private EmployeeService employeeService;

    private TrainingTO initTrainingTO;

    @Before
    //@Transactional
    public void addFirstTraining(){

        List<String> keys = new ArrayList<>();
        keys.add("spring");
        keys.add("rest");
        keys.add("java");

         initTrainingTO = createTraining("Spring for beginners", "internal","tachnical", 30,
                "2018-12-01", "2018-12-10", keys, 2000.0);

        initTrainingTO = trainingService.addTraining(initTrainingTO);

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

    @Test(expected = OptimisticEntityLockException.class)
    @Transactional
    public void testshouldReturnOptimisticLocking(){
        //given
        //TrainingTO trainingTO = trainingService.findTraining(1);

        //when
        initTrainingTO.setTitle("SPRING for begineers - Oracle");
        trainingService.updateTraining(initTrainingTO);
        initTrainingTO.setType("external");
        trainingService.updateTraining(initTrainingTO);


    }

    @Test
    @Transactional
    public void testshouldReturnSize1Trainer(){
        //given
        EmployeeTO employeeTO = createEmployee("Ania", "Testowa", "programmer");
        employeeTO = employeeService.addEmployee(employeeTO);
        TrainerTO trainerTO = createTrainer("Ania", "Testowa", "programmer", "");
        trainerTO = employeeService.addTrainer(employeeTO,trainerTO);

        //when
        trainingService.addTrainerToTraining(initTrainingTO,trainerTO);


        //then
        List<TrainerTO> trainers = trainingService.findTrainers(initTrainingTO);

        assertNotNull(trainers);
        assertEquals(1,trainers.size());
    }






}
