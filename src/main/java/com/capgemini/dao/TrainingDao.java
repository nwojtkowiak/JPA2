package com.capgemini.dao;

import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface TrainingDao extends CrudRepository<TrainingEntity, Long> {

     List<TrainingEntity> findAll();

     List<TrainerEntity> findTrainersById(long id);

     List<TrainingEntity> findByStudentsContains(StudentEntity student);


    @Query("select COALESCE(sum(t.amount),0) from TrainingEntity t join t.students s on s.id = :student  ")
     double sumCostAllTrainingForStudent(@Param("student") long id );

    @Query("select COALESCE(sum(t.amount),0) from TrainingEntity t join t.students s on s.id = :student  "
            +" where t.dateFrom between :dtFrom and :dtTo")
    double sumCostAllTrainingForStudentPerYear(@Param("student") long id, @Param("dtFrom") Date dtFrom, @Param("dtTo") Date dtTo);

    @Query("select count(t.id) from TrainingEntity t join t.students s on s.id = :student "
            +  " where t.dateFrom between :dtFrom and :dtTo ")
    int countAllTrainingForStudentPerYear(@Param("student") long id, @Param("dtFrom") Date dtFrom, @Param("dtTo") Date dtTo);

    @Query("select count(t.id) from TrainingEntity t " +
            " join EmployeeEntity e1 on e1.id = :employee " +
            " where t.dateFrom between :dtFrom and :dtTo " +
            " and  (e1.student member of t.students " +
            " or e1.trainer member of t.trainers)")
    int countAllTrainingForEmployeeInPeriod(@Param("employee") long id, @Param("dtFrom") Date dtFrom, @Param("dtTo") Date dtTo);

    @Query("select t from TrainingEntity t where :key member of t.keyWords")
    List<TrainingEntity> findByKeyWordsContains(@Param("key") String key);


    @Query("select sum(te.duration) from TrainingEntity te join te.trainers t on t.id = :trainer "
            +  " where te.dateFrom between :dtFrom and :dtTo ")
    int sumHoursAllTrainingForTrainerPerYear(@Param("trainer") long id, @Param("dtFrom") Date dtFrom, @Param("dtTo") Date dtTo );
}
