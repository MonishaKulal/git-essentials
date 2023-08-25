package com.niveus.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.niveus.businessmanager.Companybusinessmanager;
import com.niveus.entity.Company;
import com.niveus.exception.ResourceNotFoundException;
import com.niveus.model.ResponseBody;
import com.niveus.repository.CompanyRepository;

public class CompanyControllerTest {

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private Companybusinessmanager companybusinessmanager;

    @Mock
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEmployees() {
        List<Company> employees = new ArrayList<>();
        employees.add(new Company(1, "John", 19, "Mangalore", 200));
        employees.add(new Company(2, "Jane" ,20, "Bangalore", 400));

        when(companyRepository.findAll()).thenReturn(employees);

        List<Company> result = companyController.getAllEmployees();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Jane", result.get(1).getAge());
        assertEquals("Jane", result.get(1).getAddress());
        assertEquals("Jane", result.get(1).getSalary());
        
    }

    @Test
    public void testGetEmployeeById() throws ResourceNotFoundException {
        Company employee = new Company(1, "John", 30, "USA", 120);
        ResponseEntity<Company> responseEntity = ResponseEntity.ok(employee);

        when(companybusinessmanager.getEmployeeById(1)).thenReturn(responseEntity);

        ResponseEntity<Company> result = companyController.getEmployeeById(1);

        assertNotNull(result);
        assertEquals(employee.getName(), result.getBody().getName());
        assertEquals(employee.getName(), result.getBody().getAge());
        assertEquals(employee.getName(), result.getBody().getAddress());
        assertEquals(employee.getName(), result.getBody().getSalary());
    }

    @Test
    public void testCreateEmployee() {
        Company employee = new Company(1, "John", 27, "Chenai",450);
        List<ResponseBody> response = new ArrayList<>();
        response.add(new ResponseBody());

        when(companybusinessmanager.createEmployee(any(Company.class))).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<List<ResponseBody>> result = companyController.createEmployee(employee);

        assertNotNull(result);
        assertEquals(1, result.getBody().size());
        assertEquals("Employee created successfully.", result.getBody().get(0).getStatusMessage());
    }

    @Test
    public void testUpdateEmployee() throws ResourceNotFoundException {
        int employeeId = 1;
        Company updatedEmployee = new Company(employeeId, "Jevan", 67, "USA", 900);
        List<ResponseBody> response = new ArrayList<>();
        response.add(new ResponseBody());

        when(companybusinessmanager.updateEmployee(employeeId, updatedEmployee)).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<List<ResponseBody>> result = companyController.updateEmployee(employeeId, updatedEmployee);

        assertNotNull(result);
        assertEquals(1, result.getBody().size());
        assertEquals("Employee updated successfully.", result.getBody().get(0).getStatusMessage());
    }

    @Test
    public void testDeleteEmployee() throws ResourceNotFoundException {
        int employeeId = 1;
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        when(companybusinessmanager.deleteEmployee(employeeId)).thenReturn(response);

        Map<String, Boolean> result = companyController.deleteEmployee(employeeId);

        assertNotNull(result);
        assertTrue(result.get("deleted"));
    }
}

