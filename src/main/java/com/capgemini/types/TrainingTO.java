package com.capgemini.types;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TrainingTO {

    private Long id;
    private String title;
    private String type;
    private String kind;
    private Date dateFrom;
    private Date dateTo;
    private double amount;
    private int duration;
    private List<String> keyWords;
    private List<Long> students;
    private List<Long> trainers;
    // private long version;

    public TrainingTO(Long id, String title, String type, String kind, Date dateFrom, Date dateTo,
                      int duration, List<String> keyWords, double amount,
                      List<Long> students, List<Long> trainers/*, long version*/) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.kind = kind;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.duration = duration;
        this.keyWords = keyWords;
        this.amount = amount;
        this.students = students;
        this.trainers = trainers;
        //  this.version = version;
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

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
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

    public void setStudents(List<Long> students) {
        this.students = students;
    }

    public void setTrainers(List<Long> trainers) {
        this.trainers = trainers;
    }

    public List<Long> getStudents() {
        return students;
    }

    public List<Long> getTrainers() {
        return trainers;
    }

//    public long getVersion() {
//        return version;
//    }
//
//    public void setVersion(long version) {
//        this.version = version;
//    }

    public static class TrainingTOBuilder {

        private Long id;
        private String title;
        private String type;
        private String kind;
        private Date dateFrom;
        private Date dateTo;
        private int duration;
        private List<String> keyWords;
        private double amount;
        private List<Long> students = new ArrayList<>();
        private List<Long> trainers = new ArrayList<>();
        //private long version;


        public TrainingTOBuilder() {
            super();
        }


        public TrainingTOBuilder withId(Long id) {
            this.id = id;
            return this;

        }

        public TrainingTOBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public TrainingTOBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public TrainingTOBuilder withKind(String kind) {
            this.kind = kind;
            return this;
        }

        public TrainingTOBuilder withDateFrom(String date) {
            this.dateFrom = Date.valueOf(date);
            return this;

        }

        public TrainingTOBuilder withDateTo(String date) {
            this.dateTo = Date.valueOf(date);
            return this;
        }

        public TrainingTOBuilder withDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public TrainingTOBuilder withKeyWords(List<String> keys) {
            this.keyWords = keys;
            return this;
        }

        public TrainingTOBuilder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public TrainingTOBuilder withStudents(List<Long> students) {
            this.students = students;
            return this;
        }

        public TrainingTOBuilder withTrainers(List<Long> trainers) {
            this.trainers = trainers;
            return this;
        }

//        public TrainingTOBuilder withVersion(long version) {
//            this.version = version;
//            return this;
//
//        }


        public TrainingTO build() {
            checkBeforeBuild(title, type, kind, dateFrom, dateTo, duration, keyWords, amount);
            return new TrainingTO(id, title, type, kind, dateFrom, dateTo, duration,
                    keyWords, amount, students, trainers/*, version*/);
        }

        private void checkBeforeBuild(String title, String type, String kind, Date dateFrom, Date dateTo,
                                      Integer duration, List<String> keyWords, Double amount) {
            if (title == null || type == null || kind == null || dateFrom == null || dateTo == null ||
                    duration == null || keyWords == null || amount == null) {
                throw new RuntimeException("Incorrect training to be created");
            }

        }
    }

}
