package com.capgemini.types;

public class EmployeeTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private Long version;
    private Long student;
    private Long trener;


    public EmployeeTO(Long id, String firstName, String lastName,
                      String position, Long version) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getVersion() {
        return version;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getStudent() {
        return student;
    }

    public Long getTrener() {
        return trener;
    }

    public static class EmployeeTOBuilder {

        private Long id;
        private String firstName;
        private String lastName;
        private String position;
        private Long version;
        private Long student;
        private Long trener;

        public EmployeeTOBuilder() {
            super();
        }

        public EmployeeTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public EmployeeTOBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public EmployeeTOBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public EmployeeTOBuilder withPosition(String position) {
            this.position = position;
            return this;
        }

        public EmployeeTOBuilder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public EmployeeTOBuilder withStudent(Long student) {
            this.student = student;
            return this;
        }

        public EmployeeTOBuilder withTrener(Long trener) {
            this.trener = trener;
            return this;
        }


        public EmployeeTO build() {
            checkBeforeBuild(firstName, lastName, position);
            return new EmployeeTO(id, firstName, lastName, position, version);
        }

        private void checkBeforeBuild(String firstName, String lastName, String position) {
            if (firstName == null || lastName == null || position == null) {
                throw new RuntimeException("Incorrect employee to be created");
            }

        }

    }
}
