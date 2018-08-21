package com.capgemini.listeners;

import com.capgemini.domain.AbstractEntity;

import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.util.Date;


public class UpdateListener {

    @PreUpdate
    protected void onUpdate(AbstractEntity abstractEntity) {
        Date date = new Date();
        abstractEntity.setUpdatedTime(new Timestamp(date.getTime()));
    }


}