package com.capgemini.domain;

import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TRAINING")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({UpdateListener.class, InsertListener.class})
public class TrainingEntity extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String kind;

    @Column(nullable = false)
    private Date dateFrom;

    @Column(nullable = false)
    private Date dateTo;

    @Column(nullable = false)
    private int duration;

    @ElementCollection
    private List<String> keyWords;

    @Column(nullable = false)
    private double amount;

//    @Version
//    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
//    private int version;

    @ManyToMany
    @JoinTable(name = "student_training", joinColumns = {@JoinColumn(name = "training_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<StudentEntity> students;

    @ManyToMany
    @JoinTable(name = "trainer_training", joinColumns = {@JoinColumn(name = "training_id")},
            inverseJoinColumns = {@JoinColumn(name = "trainer_id")})
    private List<TrainerEntity> trainers;


    public TrainingEntity() {
        this.students = new ArrayList<>();
        this.trainers = new ArrayList<>();
    }

    public TrainingEntity(Long id, String title, String type, String kind, Date dateFrom, Date dateTo,
                          int duration, List<String> keyWords, double amount) {

        //super(version);
        this.id = id;
        this.title = title;
        this.type = type;
        this.kind = kind;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.duration = duration;
        this.keyWords = keyWords;
        this.amount = amount;
        this.students = new ArrayList<>();
        this.trainers = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getKind() {
        return kind;
    }

    public String getDateFrom() {
        return dateFrom.toString();
    }

    public String getDateTo() {
        return dateTo.toString();
    }

    public int getDuration() {
        return duration;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public double getAmount() {
        return amount;
    }

    public List<StudentEntity> getStudents() {
        return students;
    }

    public List<TrainerEntity> getTrainers() {
        return trainers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }

    public void setTrainers(List<TrainerEntity> trainers) {
        this.trainers = trainers;
    }

}
