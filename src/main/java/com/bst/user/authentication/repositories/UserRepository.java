package com.bst.user.authentication.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bst.user.authentication.entities.User;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long>{
	public User findByEmail(String email);
}
