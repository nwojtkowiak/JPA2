package com.capgemini.types;

public class EmployeeSearchCriteriaTO {
    private String officeName;
    private Long carId;
    private String positionName;

    public EmployeeSearchCriteriaTO(String officeName, Long carId, String positionName) {
        this.officeName = officeName;
        this.carId = carId;
        this.positionName = positionName;
    }

    public void setOfficeId(String officeName) {
        this.officeName = officeName;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public void setPositionId(String positionName) {
        this.positionName = positionName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public Long getCarId() {
        return carId;
    }

    public String getPositionName() {
        return positionName;
    }

    public static class EmployeeSearchCriteriaBuilder {

        private String officeName;
        private String positionName;
        private Long carId;


        public EmployeeSearchCriteriaBuilder() {
            super();
        }

        public EmployeeSearchCriteriaBuilder withOffice(String officeName) {
            this.officeName = officeName;
            return this;
        }

        public EmployeeSearchCriteriaBuilder withPosition(String positionName) {
            this.positionName = positionName;
            return this;
        }

        public EmployeeSearchCriteriaBuilder withCar(Long carId) {
            this.carId = carId;
            return this;
        }

        public EmployeeSearchCriteriaTO build() {
            checkBeforeBuild(officeName, carId, positionName);
            return new EmployeeSearchCriteriaTO( officeName, carId,positionName);
        }

        private void checkBeforeBuild( String officeName, Long carId, String positionName ) {
            if (officeName == null &&  positionName == null && carId == null) {
                throw new RuntimeException("Incorrect EmployeeSearchCriteria to be created");
            }

        }

    }




}
