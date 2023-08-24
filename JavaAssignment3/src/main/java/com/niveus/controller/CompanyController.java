package com.niveus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niveus.businessmanager.Companybusinessmanager;
import com.niveus.entity.Company;
import com.niveus.exception.ResourceNotFoundException;
import com.niveus.model.ResponseBody;
import com.niveus.repository.CompanyRepository;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
	
	@Autowired
	Companybusinessmanager companybusinessmanager;

	@Autowired
	private CompanyRepository companyRepository;
	List<ResponseBody> responseBody = null;
	ResponseBody responseStatus = null;

	@GetMapping("/ping")
	private static String getStatus() {
		return "Assessment 3 Application Running Successfully.........";

	}

	@GetMapping("/employees")
	public List<Company> getAllEmployees() {
		return companyRepository.findAll();
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Company> getEmployeeById(@PathVariable(value = "id") int employeeId)
			throws ResourceNotFoundException {
	
		return companybusinessmanager.getEmployeeById(employeeId);
	}

	@PostMapping("/employees")
	public ResponseEntity<List<ResponseBody>>  createEmployee(@Valid @RequestBody Company employee) {
		return companybusinessmanager.createEmployee(employee);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<List<ResponseBody>> updateEmployee(@PathVariable(value = "id") int employeeId,
			@Valid @RequestBody Company employeeDetails) throws ResourceNotFoundException {
		return companybusinessmanager.updateEmployee(employeeId,employeeDetails);
	

	}

	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") int employeeId)
			throws ResourceNotFoundException {
		return companybusinessmanager.deleteEmployee(employeeId);
	}
}