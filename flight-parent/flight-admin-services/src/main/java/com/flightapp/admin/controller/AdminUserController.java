package com.flightapp.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.admin.service.AdminUserService;
import com.flightapp.api.LoginApi;
import com.flightapp.models.AdminUser;
import com.flightapp.models.ApiError;
import com.flightapp.models.Token;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@ApiResponses(value = {
		@ApiResponse(responseCode = "401", description = "Unauthorized", content = {
				@Content(mediaType = "application/json", schema = @Schema(anyOf = ApiError.class)) }),
		@ApiResponse(responseCode = "400", description = "Bad Request", content = {
				@Content(mediaType = "application/json", schema = @Schema(anyOf = ApiError.class)) }) })
@RequestMapping("admin")
@CrossOrigin
public class AdminUserController implements LoginApi {

	@Autowired
	private AdminUserService adminUserService;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful", content = {
			@Content(mediaType = "application/json", schema = @Schema(anyOf = Token.class)) }) })
	@Override
	public ResponseEntity<Token> login(AdminUser body) {
		System.out.println(body);
		return adminUserService.validateAdminUser(body);
	}
}
