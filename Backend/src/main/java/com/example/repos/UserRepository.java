package com.example.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.example.mysql.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	public List<User> findByUsernameContaining(String username);
	public List<User> findByUsername(String username);
}
