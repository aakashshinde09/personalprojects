package com.backend.service;

import java.util.List;

import com.backend.model.User;

public interface UserService {
	User registerUser(User user);

	List<User> getUsers();

	void deleteUser(String email);

	User getUser(String email);
}