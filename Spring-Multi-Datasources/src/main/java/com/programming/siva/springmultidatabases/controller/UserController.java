package com.programming.siva.springmultidatabases.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programming.siva.springmultidatabases.dto.UsersResponse;
import com.programming.siva.springmultidatabases.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/fetchUsers")
	public ResponseEntity<List<UsersResponse>> getUsers()	{
		
		List<UsersResponse> employees = userService.getAllUsers();
		return ResponseEntity.ok(employees);

	}
	
}
