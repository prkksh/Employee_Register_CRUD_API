package com.project.employeeregister;

import com.project.employeeregister.controller.EmployeeController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeRegisterApplicationTests {

	@Autowired
	EmployeeController employeeController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(employeeController);
	}

}
