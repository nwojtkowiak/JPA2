package com.capgemini.domain;

import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

//@Entity
//@Table(name = "INTERNAL_TRAINER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({UpdateListener.class, InsertListener.class})
public class InternalTrainer extends AbstractEntity implements  Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    public InternalTrainer(){

    }

}
