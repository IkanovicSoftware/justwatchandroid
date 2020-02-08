package com.example.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.mysql.Friend;
import com.example.mysql.Membership;

public interface MembershipRepository extends CrudRepository<Membership, Integer> {
	public List<Membership> findAllByGid(int gid);
	public List<Membership> findAllByUid(int gid);
}
