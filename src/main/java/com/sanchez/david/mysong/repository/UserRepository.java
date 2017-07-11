package com.sanchez.david.mysong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanchez.david.mysong.component.User;

public interface UserRepository extends JpaRepository<User, String> {
}
