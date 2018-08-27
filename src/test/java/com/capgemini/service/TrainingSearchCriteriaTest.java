package com.capgemini.service;

import com.capgemini.exceptions.ParticipationInCourseException;
import com.capgemini.exceptions.TooLargeTotalAmountException;
import com.capgemini.exceptions.TooMuchTrainingException;
import com.capgemini.types.TrainingSearchCriteriaTO;
import com.capgemini.types.TrainingTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.capgemini.service.HelpMethods.createTraining;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class TrainingSearchCriteriaTest {

    @Autowired
    private TrainingService trainingService;

    @Test
    @Transactional
    public void testShouldReturn4TrainingWithEmptySearchCriteria() throws TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

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

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTO.TrainingSearchCriteriaTOBuilder()
                .build();

        //when
        List<TrainingTO> trainings = trainingService.findTrainingsBySearchCriteria(criteria);

        //then
        assertNotNull(trainings);
        assertEquals(3, trainings.size());
    }

    @Test
    @Transactional
    public void testShouldReturn4TrainingWithTitleSearchCriteria() throws TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

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

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTO.TrainingSearchCriteriaTOBuilder()
                .withTitle("for beginners")
                .build();

        //when
        List<TrainingTO> trainings = trainingService.findTrainingsBySearchCriteria(criteria);

        //then
        assertNotNull(trainings);
        assertEquals(3, trainings.size());
    }

    @Test
    @Transactional
    public void testShouldReturn2TrainingWithTypeSearchCriteria() throws TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

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

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTO.TrainingSearchCriteriaTOBuilder()
                .withTitle("for beginners")
                .withType("internal")
                .build();
        //when
        List<TrainingTO> trainings = trainingService.findTrainingsBySearchCriteria(criteria);

        //then
        assertNotNull(trainings);
        assertEquals(1, trainings.size());
    }

    @Test
    @Transactional
    public void testShouldReturn1TrainingWithKindSearchCriteria() throws TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

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

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTO.TrainingSearchCriteriaTOBuilder()
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
    public void testShouldReturn1TrainingWithDateSearchCriteria() throws TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

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

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTO.TrainingSearchCriteriaTOBuilder()
                .withDate(Date.valueOf("2019-10-21"))
                .build();

        //when
        List<TrainingTO> trainings = trainingService.findTrainingsBySearchCriteria(criteria);

        //then
        assertNotNull(trainings);
    }

    @Test
    @Transactional
    public void testShouldReturn2TrainingWithAmountFromSearchCriteria() throws TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

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

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTO.TrainingSearchCriteriaTOBuilder()
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
    public void testShouldReturn1TrainingWithAmountFromAdnToSearchCriteria() throws TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

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

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTO.TrainingSearchCriteriaTOBuilder()

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
    public void testShouldReturn2TrainingWithAllSearchCriteria() throws TooLargeTotalAmountException, ParticipationInCourseException, TooMuchTrainingException {

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

        TrainingSearchCriteriaTO criteria = new TrainingSearchCriteriaTO.TrainingSearchCriteriaTOBuilder()
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
}
