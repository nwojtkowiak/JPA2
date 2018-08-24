package com.capgemini.dao;

import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.Year;
import java.util.List;

public interface TrainingDao extends CrudRepository<TrainingEntity, Long> {

     List<TrainingEntity> findAll();

     List<TrainerEntity> findTrainersById(long id);

     List<TrainingEntity> findByStudentsContains(StudentEntity student);


    @Query("select COALESCE(sum(t.amount),0) from TrainingEntity t join t.students s on s.id = :student  ")
     Double sumCostAllTrainingForStudent(@Param("student") long id );

   /* @Query("select sum(t.amount) from TrainingEntity t join t.students s on s.id = :student  "
          //  +" and t.dateFrom between :dtFrom and :dtTo"
    )
    Long sumCostAllTrainingForStudentPerYear(@Param("student") long id, @Param("dtFrom") Year dtFrom, @Param("dtTo") Year dtTo);
*/
    @Query("select count(t.id) from TrainingEntity t join t.students s on s.id = :student  ")
    Long countAllTrainingForStudentPerYear(@Param("student") long id );
}
