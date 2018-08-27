package com.capgemini.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@MappedSuperclass
public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Version
    public int version;

    public void setCreatedTime(final Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}