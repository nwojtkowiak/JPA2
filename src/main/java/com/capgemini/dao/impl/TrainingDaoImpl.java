package com.capgemini.dao.impl;

import com.capgemini.dao.TrainingQueryDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.TrainingEntity;
import com.capgemini.types.TrainingSearchCriteriaTO;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.LinkedList;
import java.util.List;

public class TrainingDaoImpl extends AbstractDao<TrainingEntity, Long> implements TrainingQueryDao {


    @Override
    public List<TrainingEntity> findTrainingsByCritetia(TrainingSearchCriteriaTO searchCriteria) {
        TypedQuery<TrainingEntity> queryTraining;


        StringBuilder builderWhere = new StringBuilder();
        builderWhere.append(" where ");

        boolean title = false;
        boolean type = false;
        boolean kind = false;
        boolean date = false;
        boolean amountFrom = false;
        boolean amountTo = false;

        if (searchCriteria.getTitle() != null) {
            builderWhere.append(" t.title = :title");
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

        queryTraining = entityManager.createQuery(
                "select e from TrainingEntity t "  + builderWhere.toString()
                , TrainingEntity.class);

        if (title) {
            queryTraining.setParameter("title", searchCriteria.getTitle());
        }
        if (type) {
            queryTraining.setParameter("type", searchCriteria.getType());
        }
        if (kind) {
            queryTraining.setParameter("kind", searchCriteria.getKind());
        }

        if (date) {
            queryTraining.setParameter("dateFrom", searchCriteria.getDate());
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
