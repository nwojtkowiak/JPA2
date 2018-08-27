package com.capgemini.types;

import java.sql.Date;

public class TrainingSearchCriteriaTO {
    private String title;
    private String type;
    private String kind;
    private Date date;
    private Double amountFrom;
    private Double amountTo;

    public TrainingSearchCriteriaTO(String title, String type, String kind,
                                    Date date, Double amountFrom, Double amountTo) {
        this.title = title;
        this.type = type;
        this.kind = kind;
        this.date = date;
        this.amountFrom = amountFrom;
        this.amountTo = amountTo;
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

    public Date getDate() {
        return date;
    }

    public Double getAmountFrom() {
        return amountFrom;
    }

    public Double getAmountTo() {
        return amountTo;
    }

    public static class TrainingSearchCriteriaTOBuilder {

        private String title;
        private String type;
        private String kind;
        private Date date;
        private Double amountFrom;
        private Double amountTo;


        public TrainingSearchCriteriaTOBuilder() {
            super();
        }

        public TrainingSearchCriteriaTOBuilder(String title, String type, String kind,
                                               Date date, Double amountFrom, Double amountTo) {
            this.title = title;
            this.type = type;
            this.kind = kind;
            this.date = date;
            this.amountFrom = amountFrom;
            this.amountTo = amountTo;
        }

        public TrainingSearchCriteriaTOBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public TrainingSearchCriteriaTOBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public TrainingSearchCriteriaTOBuilder withKind(String kind) {
            this.kind = kind;
            return this;
        }

        public TrainingSearchCriteriaTOBuilder withDate(Date date) {
            this.date = date;
            return this;
        }


        public TrainingSearchCriteriaTOBuilder withAmountFrom(Double amountFrom) {
            this.amountFrom = amountFrom;
            return this;
        }

        public TrainingSearchCriteriaTOBuilder withAmountTo(Double amountTo) {
            this.amountTo = amountTo;
            return this;
        }


        public TrainingSearchCriteriaTO build() {
            return new TrainingSearchCriteriaTO( title, type, kind, date, amountFrom, amountTo);
        }



    }




}
