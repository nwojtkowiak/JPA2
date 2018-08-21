package com.capgemini.listeners;

import com.capgemini.domain.AbstractEntity;

import javax.persistence.PrePersist;
import java.sql.Timestamp;
import java.util.Date;


public class InsertListener {

    @PrePersist
    protected void onCreate(AbstractEntity abstractEntity) {
        Date date = new Date();
        abstractEntity.setCreatedTime(new Timestamp(date.getTime()));
    }


}