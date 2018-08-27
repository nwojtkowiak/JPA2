package com.capgemini.service;

import com.capgemini.types.TrainingTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.capgemini.service.HelpMethods.createTraining;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class OptimisticLockingTest {

    @Autowired
    private TrainingService trainingService;

   /* @Test(expected = OptimisticLockingFailureException.class)
    //@Transactional
    public void testShouldReturnOptimisticLocking(){
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


    }*/
}
