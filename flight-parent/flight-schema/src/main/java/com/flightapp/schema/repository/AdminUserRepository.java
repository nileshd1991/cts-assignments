package com.flightapp.schema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.schema.model.AdminUser;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Integer>{

	AdminUser findByUserName(String username);

}
