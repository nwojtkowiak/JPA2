package com.capgemini.domain;

import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EMPLOYEE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({UpdateListener.class, InsertListener.class})
public class EmployeeEntity extends AbstractEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 45)
    private String firstName;

    @Column(nullable = false, length = 45)
    private String lastName;

    @Column(nullable = false, length = 45)
    private String position;


    @ManyToMany
    @JoinTable(name = "employee_trainer", joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "trainer_id")})
    private List<TrainerEntity> trainers;

    @ManyToMany
    @JoinTable(name = "employee_student", joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<StudentEntity> students;


    // for hibernate
    public EmployeeEntity() {
        this.trainers = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public EmployeeEntity(Long id, String firstName, String lastName, String position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.trainers = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public Long getId() {
        return id;
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

    public List<StudentEntity> getStudents() {
        return students;
    }

    public List<TrainerEntity> getTrainers() {
        return trainers;
    }
}
