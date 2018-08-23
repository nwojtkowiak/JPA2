package com.capgemini.types;

public class TrainerTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private String companyName;
    private Long version;


    public TrainerTO(Long id, String firstName, String lastName,
                     String position, String companyName,
                     Long version) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.companyName = companyName;
        this.version = version;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public static class TrainerTOBuilder {

        private Long id;
        private String firstName;
        private String lastName;
        private String position;
        private String companyName;
        private Long version;


        public TrainerTOBuilder() {
            super();
        }

        public TrainerTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TrainerTOBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public TrainerTOBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public TrainerTOBuilder withPosition(String position) {
            this.position = position;
            return this;
        }

        public TrainerTOBuilder withCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public TrainerTOBuilder withVersion(Long version) {
            this.version = version;
            return this;
        }


        public TrainerTO build() {
            checkBeforeBuild(firstName, lastName,position);
            return new TrainerTO(id, firstName, lastName,  position ,companyName, version);
        }

        private void checkBeforeBuild(String firstName, String lastName, String position) {
            if (firstName == null || lastName == null || position == null) {
                throw new RuntimeException("Incorrect trainer to be created");
            }

        }

    }
}
