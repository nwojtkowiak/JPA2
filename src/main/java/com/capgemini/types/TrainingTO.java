package com.capgemini.types;

import java.sql.Date;
import java.util.List;

public class TrainingTO {

    private Long id;
    private String title;
    private String type;
    private String kind;
    private Date dateFrom;
    private Date dateTo;
    private int duration;
    private List<String> keyWords;
    private double amount;
    private List<Long> students;

    public TrainingTO(Long id, String title, String type, String kind, Date dateFrom, Date dateTo,
                             int duration, List<String> keyWords, double amount) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.kind = kind;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.duration = duration;
        this.keyWords = keyWords;
        this.amount = amount;
    }

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
        private List<Long> students;

        public TrainingTOBuilder() {
            super();
        }



        public TrainingTOBuilder withId(long id) {
            this.id = id;
            return this;

        }
        public TrainingTOBuilder withTitle(String title){
            this.title = title;
            return this;
        }

        public TrainingTOBuilder withType(String type){
            this.type = type;
            return this;
        }

        public TrainingTOBuilder withKind(String kind){
            this.kind = kind;
            return this;
        }

        public TrainingTOBuilder withDateFrom(String date){
            this.dateFrom = Date.valueOf(date);
            return this;

        }

        public TrainingTOBuilder withDateTo(String date){
            this.dateTo = Date.valueOf(date);
            return this;
        }

        public TrainingTOBuilder withDuration(int duration){
            this.duration = duration;
            return this;
        }

        public TrainingTOBuilder withKeyWords(List<String> keys){
            this.keyWords = keys;
            return this;
        }

        public TrainingTOBuilder withAmount(double amount){
            this.amount = amount;
            return this;
        }

        public TrainingTO build() {
            checkBeforeBuild(title, type, kind, dateFrom, dateTo, duration, keyWords, amount);
            return new TrainingTO(id, title, type, kind, dateFrom, dateTo, duration, keyWords, amount);
        }

        private void checkBeforeBuild(String title, String type, String kind, Date dateFrom, Date dateTo,
                                      Integer duration, List<String> keyWords, Double amount)
        {
            if (title == null || type == null || kind == null || dateFrom == null || dateTo == null ||
                    duration == null || keyWords == null || amount == null ) {
                throw new RuntimeException("Incorrect training to be created");
            }

        }
    }
}