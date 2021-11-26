package com.project.employeeregister.controller;

import com.project.employeeregister.exception.EmployeeNotFoundException;
import com.project.employeeregister.model.Employee;
import com.project.employeeregister.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
class EmployeeControllerTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeController empCon;

    @Test
    void requesting_employees_list_should_return_all_employees() {
        Employee employee1 = new Employee("Adam","Smith","SE",50000);
        Employee employee2 = new Employee("Brad","Pitt","SE",50000);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> empList = empCon.getAllEmployees();

        assertEquals(2,empList.size());
    }

    @Test
    void searching_for_existent_employee_by_id_should_return_matching_employee() throws EmployeeNotFoundException {
        Employee employee1 = new Employee("Adam","Smith","SE",50000);
        Mockito.when(employeeRepository.findById(1l)).thenReturn(Optional.of(employee1));
        ResponseEntity<Employee> result = empCon.getEmployeeById(1l);

        assertEquals("Adam",result.getBody().getFirstName());
    }

    @Test
    void searching_for_non_existent_employee_by_id_should_throw_exception() throws EmployeeNotFoundException {
        Employee employee1 = new Employee("Adam","Smith","SE",50000);
        Mockito.when(employeeRepository.findById(1l)).thenReturn(Optional.of(employee1));

        assertThrows(EmployeeNotFoundException.class,()->{empCon.getEmployeeById(2l);});
    }

    @Test
    void create_employee_should_call_save_1_time() {
        Employee employee1 = new Employee("Adam","Smith","SE",50000);
        empCon.createEmployee(employee1);

        Mockito.verify(employeeRepository,Mockito.times(1)).save(employee1);
    }

    @Test
    void update_existent_employee_by_id_should_return_expected_object() throws EmployeeNotFoundException {
        Employee employee1 = new Employee("Adam","Smith","SE",50000);
        employee1.setSalary(60000);
        Employee updatedEmployee = empCon.createEmployee(employee1);

        assertEquals(60000,employee1.getSalary());
    }

    @Test
    void update_non_existent_employee_by_id_should_throw_exception() throws EmployeeNotFoundException {
        Employee employee = new Employee("Adam","Smith","SE",50000);
        empCon.createEmployee(employee);
        Employee employeeNew = new Employee("Adam","Smith","SE",60000);

        assertThrows(EmployeeNotFoundException.class,()->{empCon.updateEmployee(2l,employeeNew);});
    }

    @Test
    void delete_existent_employee_by_id_should_delete_employee() throws EmployeeNotFoundException {
        Employee employee = new Employee("Adam","Smith","SE",50000);
        Mockito.when(employeeRepository.findById(1l)).thenReturn(Optional.of(employee));
        empCon.deleteEmployee(1l);

        Mockito.verify(employeeRepository,Mockito.times(1)).delete(employee);
    }

    @Test
    void delete_non_existent_employee_by_id_should_throw_exception() throws EmployeeNotFoundException {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(()->empCon.deleteEmployee(1l));
    }
}