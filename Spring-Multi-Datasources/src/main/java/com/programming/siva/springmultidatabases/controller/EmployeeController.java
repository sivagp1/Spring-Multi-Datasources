package com.programming.siva.springmultidatabases.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programming.siva.springmultidatabases.dto.EmployeesResponse;
import com.programming.siva.springmultidatabases.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/fetchEmployees")
	public ResponseEntity<List<EmployeesResponse>> getEmployee()	{
		
		List<EmployeesResponse> employees = employeeService.getAllEmployees();
		return ResponseEntity.ok(employees);

	}

}
