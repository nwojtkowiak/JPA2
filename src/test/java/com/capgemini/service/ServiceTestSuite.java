package com.capgemini.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TrainingServiceTest.class, EmployeeServiceTest.class, TrainingSearchCriteriaTest.class })
public class ServiceTestSuite {

}

