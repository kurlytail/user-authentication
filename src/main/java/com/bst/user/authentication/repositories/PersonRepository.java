package com.bst.user.authentication.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bst.user.authentication.entities.Person;

@RepositoryRestResource
public interface PersonRepository extends CrudRepository<Person, Long>{
	public Person findByEmail(String email);
}
