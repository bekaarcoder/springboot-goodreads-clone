package com.blitzstriker.goodreads.repositories;

import com.blitzstriker.goodreads.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
