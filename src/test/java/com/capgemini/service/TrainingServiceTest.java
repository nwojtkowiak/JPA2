package com.capgemini.service;

import com.capgemini.types.TrainingTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.capgemini.service.HelpMethods.createTraining;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class TrainingServiceTest {

    @Autowired
    private TrainingService trainingService;

    @Test
    @Transactional
    public void testshouldReturnSize1AfterAddTraining(){
        //given
        List<String> keys = Arrays.asList("sql","oracle");
        TrainingTO trainingTO = createTraining("SQL for beginners", "internal","tachnical", 2,
                "2018-10-01", "2018-10-01", keys, 2000.0);
        //when
        TrainingTO saved = trainingService.addTraining(trainingTO);

        //then
        Long id = new Long(1);
        assertNotNull(saved);
        assertEquals(id,saved.getId());
    }

    

}
