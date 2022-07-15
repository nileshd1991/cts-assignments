package com.flightapp.admin.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.models.AdminUser;
import com.flightapp.models.Token;

@Service
public interface AdminUserService {

	ResponseEntity<Token> validateAdminUser(AdminUser body);

}
