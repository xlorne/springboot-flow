package com.example.flow.repository;

import com.codingapi.springboot.fast.jpa.repository.FastRepository;
import com.example.flow.domain.User;

import java.util.List;

public interface UserRepository extends FastRepository<User,Long> {

    User findByName(String name);

    List<User> findByRole(String role);
}
