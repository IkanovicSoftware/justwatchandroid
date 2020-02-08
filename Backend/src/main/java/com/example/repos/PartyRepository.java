package com.example.repos;

import org.springframework.data.repository.CrudRepository;
import com.example.mysql.Party;

public interface PartyRepository extends CrudRepository<Party, Integer> {

}
