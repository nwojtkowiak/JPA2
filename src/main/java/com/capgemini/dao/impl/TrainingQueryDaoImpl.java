package com.capgemini.dao.impl;

import com.capgemini.dao.TrainingQueryDao;
import com.capgemini.domain.TrainingEntity;

public class TrainingQueryDaoImpl extends AbstractDao<TrainingEntity, Long> implements TrainingQueryDao {


    @Override
    public long sumCostAllTrainingForStudent(long student) {
        return 0;
    }
}
