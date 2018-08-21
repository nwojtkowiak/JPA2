package com.capgemini.types;

public class EmployeeTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String position;


    public EmployeeTO(Long id, String firstName, String lastName,
                      String position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
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

    public String getPosition() { return position; }

    public static class EmployeeTOBuilder {

        private Long id;
        private String firstName;
        private String lastName;
        private String position;

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


        public EmployeeTO build() {
            checkBeforeBuild(firstName, lastName,position);
            return new EmployeeTO(id, firstName, lastName,  position );
        }

        private void checkBeforeBuild(String firstName, String lastName, String position) {
            if (firstName == null || lastName == null || position == null) {
                throw new RuntimeException("Incorrect employee to be created");
            }

        }

    }
}
