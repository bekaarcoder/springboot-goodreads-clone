package com.blitzstriker.goodreads.repositories;

import com.blitzstriker.goodreads.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
