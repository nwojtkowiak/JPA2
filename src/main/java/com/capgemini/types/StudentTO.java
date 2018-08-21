package com.capgemini.types;

public class StudentTO {

    private Long id;
    private Long boss;
    private short grade;

    public StudentTO() {
    }

    public StudentTO(Long id, Long boss, short grade) {
        this.id = id;
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

    public static class StudentTOBuilder {

        private Long id;
        private Long boss;
        private short grade;

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



        public StudentTO build() {
            checkBeforeBuild(grade, boss);
            return new StudentTO(id, boss, grade);
        }

        private void checkBeforeBuild(Short grade, Long boss) {
            if (grade == null || boss == null) {
                throw new RuntimeException("Incorrect student to be created");
            }

        }


    }
}
