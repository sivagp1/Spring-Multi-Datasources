package com.programming.siva.springmultidatabases.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programming.siva.springmultidatabases.dto.EmployeesResponse;
import com.programming.siva.springmultidatabases.exceptions.NoDataFoundException;
import com.programming.siva.springmultidatabases.salary.Employee;
import com.programming.siva.springmultidatabases.salary.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<EmployeesResponse> getAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new NoDataFoundException("No employee list found, please add.");
        }
        return employees.stream()
        		.map(this::mapToEmployeesResponse)
                .collect(Collectors.toList());
     }
	
	private EmployeesResponse mapToEmployeesResponse(Employee employee) {
        EmployeesResponse response = new EmployeesResponse();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setHra(employee.getHra());
        response.setBasicSalary(employee.getBasicSalary());
        response.setContact(employee.getContact());
        return response;
    }

}
