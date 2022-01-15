package com.consyn.app.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consyn.app.dao.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	 
	Optional<User> findByEmail(String userId);

	public User save(User user);
	
	boolean existsByEmail(String userId);


}
