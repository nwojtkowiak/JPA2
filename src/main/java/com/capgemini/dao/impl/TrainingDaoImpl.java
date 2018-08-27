package com.capgemini.dao.impl;

import com.capgemini.dao.TrainingQueryDao;
import com.capgemini.domain.QStudentEntity;
import com.capgemini.domain.QTrainingEntity;
import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainingEntity;
import com.capgemini.types.TrainingSearchCriteriaTO;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import javafx.util.Pair;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.LinkedList;
import java.util.List;

public class TrainingDaoImpl extends AbstractDao<TrainingEntity, Long> implements TrainingQueryDao {

    public List<StudentEntity> findStudentsWithLongestDuration(){
        QTrainingEntity training = QTrainingEntity.trainingEntity;
        QStudentEntity student = QStudentEntity.studentEntity;

        JPAQuery<StudentEntity> queryStudents = new JPAQuery<>(entityManager);
        JPAQuery<Long> maxQuery = new JPAQuery<>(entityManager);

        Integer maxDuration = maxQuery.select(training.duration.sum())
                .from(training).innerJoin(training.students, student)
                .groupBy(student.id)
                .orderBy(training.duration.sum().desc())
                .fetchFirst();

        List<StudentEntity> students = queryStudents.select(student).distinct()
                .from(training).innerJoin(training.students, student)
               .where(student.id.in(JPAExpressions.select(student.id).from(training).innerJoin(training.students,student)
                        .groupBy(student.id).having(training.duration.sum().eq(maxDuration))))
                .fetch();

       return students;
    }

    public List<TrainingEntity> findTrainingWithMostEditions(){
        QTrainingEntity training = QTrainingEntity.trainingEntity;


        JPAQuery<TrainingEntity> queryTraining = new JPAQuery<>(entityManager);
        JPAQuery<Long> maxQuery = new JPAQuery<>(entityManager);

        Long maxCount = maxQuery.select(training.title.count())
                .from(training)
                .groupBy(training.title)
                .orderBy(training.id.count().desc())
                .fetchFirst();

        List<String> trainingsTitle = queryTraining.select(training.title).distinct()
                .from(training)
                .where(training.title.in(JPAExpressions.select(training.title)
                        .from(training)
                        .groupBy(training.title).having(training.title.count().eq(maxCount))))
                .fetch();

        QTrainingEntity t = new QTrainingEntity("t");
        List<Long> trainingsIds = queryTraining.select(training.id).from(training)
                .where(training.title
                        .in(trainingsTitle)
                        .and(training.id.in((JPAExpressions
                                .select(t.id.min())
                                .from(t)
                                .where(t.title.eq(training.title))
                        ))))
                .fetch();

        List<TrainingEntity> trainings = queryTraining.select(training).from(training)
                .where(training.id.in(trainingsIds))
                .fetch();

        return trainings;
    }

    @Override
        public List<TrainingEntity> findTrainingsByCriteria(TrainingSearchCriteriaTO searchCriteria) {
        TypedQuery<TrainingEntity> queryTraining;


        StringBuilder builderWhere = new StringBuilder();


        boolean title = false;
        boolean type = false;
        boolean kind = false;
        boolean date = false;
        boolean amountFrom = false;
        boolean amountTo = false;

        if (searchCriteria.getTitle() != null) {
            builderWhere.append(" t.title like :title");
            title = true;
        }

        if (searchCriteria.getType() != null) {
            if(title){
                builderWhere.append(" and ");
            }
            builderWhere.append(" t.type = :type");
            type = true;

        }
        if (searchCriteria.getKind() != null) {
            if(title || type){
                builderWhere.append(" and ");
            }
            builderWhere.append(" t.kind = :kind");
            kind = true;
        }

        if (searchCriteria.getDate() != null) {
            if(title || type || kind){
                builderWhere.append(" and ");
            }
            builderWhere.append(" :date between t.dateFrom and t.dateTo");
            date = true;
        }


        if (searchCriteria.getAmountFrom() != null) {
            if(title || type || kind || date ){
                builderWhere.append(" and ");
            }
            builderWhere.append(" t.amount >= :amountFrom");
            amountFrom = true;
        }

        if (searchCriteria.getAmountTo() != null) {
            if(title || type || kind || date || amountFrom){
                builderWhere.append(" and ");
            }
            builderWhere.append(" t.amount <= :amountTo");
            amountTo = true;
        }

        if(builderWhere.length() > 0){
            builderWhere.insert(0, "where");
        }
        queryTraining = entityManager.createQuery(
                "select t from TrainingEntity t "  + builderWhere.toString()
                , TrainingEntity.class);

        if (title) {
            queryTraining.setParameter("title", "%" + searchCriteria.getTitle() +"%");
        }
        if (type) {
            queryTraining.setParameter("type", searchCriteria.getType());
        }
        if (kind) {
            queryTraining.setParameter("kind", searchCriteria.getKind());
        }

        if (date) {
            queryTraining.setParameter("date", searchCriteria.getDate());
        }

        if (amountFrom) {
            queryTraining.setParameter("amountFrom", searchCriteria.getAmountFrom());
        }

        if (amountTo) {
            queryTraining.setParameter("amountTo", searchCriteria.getAmountTo());
        }


        try {
            return queryTraining.getResultList();

        } catch (NoResultException e) {
            return new LinkedList<>();
        }
    }
}
