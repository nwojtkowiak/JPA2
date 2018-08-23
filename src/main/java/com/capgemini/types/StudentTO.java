package com.capgemini.types;

public class StudentTO {

    private Long id;
    private Long boss;
    private short grade;
    private String firstName;
    private String lastName;
    private String position;
    private Long version;

    public StudentTO() {
    }

    public StudentTO(Long id,String firstName, String lastName,
                     String position, Long version, Long boss, short grade) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.version = version;
        this.boss = boss;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public Long getBoss() {
        return boss;
    }

    public short getGrade() {
        return grade;
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

    public Long getVersion() {
        return version;
    }

    public static class StudentTOBuilder {

        private Long id;
        private Long boss;
        private short grade;
        private String firstName;
        private String lastName;
        private String position;
        private Long version;

        public StudentTOBuilder() {
            super();
        }

        public StudentTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public StudentTOBuilder withBoss(Long boss) {
            this.boss = boss;
            return this;
        }

        public StudentTOBuilder withGrade(short grade) {
            this.grade = grade;
            return this;
        }

        public StudentTOBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public StudentTOBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public StudentTOBuilder withPosition(String position) {
            this.position = position;
            return this;
        }

        public StudentTOBuilder withVersion(Long version) {
            this.version = version;
            return this;
        }



        public StudentTO build() {
            checkBeforeBuild(firstName, lastName, position);
            return new StudentTO(id, firstName, lastName,  position, version, boss, grade);
        }

        private void checkBeforeBuild(String firstName, String lastName, String position) {
            if (firstName == null || lastName == null || position == null) {
                throw new RuntimeException("Incorrect student to be created");
            }

        }


    }
}
