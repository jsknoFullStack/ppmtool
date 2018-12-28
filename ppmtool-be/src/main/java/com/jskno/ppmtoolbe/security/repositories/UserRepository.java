package com.jskno.ppmtoolbe.security.repositories;

import com.jskno.ppmtoolbe.security.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User getById(Long id);  // Did it only to avoid the Optional response from findById
}
