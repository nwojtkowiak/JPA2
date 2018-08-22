package com.capgemini.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@MappedSuperclass
public class AbstractEntity {

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Version
    public long version;

    public void setCreatedTime(final Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }
}