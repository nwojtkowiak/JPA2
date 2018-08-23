package com.capgemini.domain;


import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "STUDENT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({UpdateListener.class, InsertListener.class})
public class StudentEntity extends AbstractEntity  implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 45)
    private String firstName;

    @Column(nullable = false, length = 45)
    private String lastName;

    @Column(nullable = false, length = 45)
    private String position;

    @Column(nullable = false)
    private int grade;

    @OneToOne
    @JoinColumn(name = "boss_id")
    private EmployeeEntity boss;


    // for hibernate
    public StudentEntity() {
    }

    public StudentEntity(String firstName, String lastName, String position, int grade) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.grade = grade;
    }

    public StudentEntity(String firstName, String lastName, String position, int grade, EmployeeEntity boss) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.grade = grade;
        this.boss = boss;
    }

    public StudentEntity(Long id, String firstName, String lastName, String position, int grade) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.grade = grade;
    }

    public StudentEntity(Long id, String firstName, String lastName, String position, int grade, EmployeeEntity boss) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.grade = grade;
        this.boss = boss;
    }


    public Long getId() {
        return id;
    }

    public int getGrade() {
        return grade;
    }

    public EmployeeEntity getBoss() {
        return boss;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }
}
