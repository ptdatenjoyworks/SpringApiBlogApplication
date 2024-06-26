package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserNameOrEmail(String userName,String email);

    Optional<User> findByUserName (String userName);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);
}
