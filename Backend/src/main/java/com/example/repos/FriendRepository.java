package com.example.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.example.mysql.Friend;
import com.example.mysql.User;

public interface FriendRepository extends CrudRepository<Friend, Integer> {
	public List<Friend> findByUid(int uid);
}
