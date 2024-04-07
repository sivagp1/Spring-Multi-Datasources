package com.programming.siva.springmultidatabases.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programming.siva.springmultidatabases.dto.UsersResponse;
import com.programming.siva.springmultidatabases.exceptions.NoDataFoundException;
import com.programming.siva.springmultidatabases.security.User;
import com.programming.siva.springmultidatabases.security.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UsersResponse> getAllUsers() {
		List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NoDataFoundException("No users list found, please add.");
        }
        return users.stream()
        		.map(this::mapToUsersResponse)
                .collect(Collectors.toList());
     }
	
	private UsersResponse mapToUsersResponse(User user) {
		UsersResponse response = new UsersResponse();
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setUsername(user.getUsername());
        response.setPassword(user.getPassword());
        return response;
    }

}
