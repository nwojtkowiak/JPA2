package com.capgemini.domain;

import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TRAINER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({UpdateListener.class, InsertListener.class})
public class TrainerEntity extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String position;

    @Column(nullable = true)
    private String companyName;






    public TrainerEntity(){

    }
    public TrainerEntity(Long id, String firstName, String lastName, String position, String companyName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.companyName = companyName;
    }
}
