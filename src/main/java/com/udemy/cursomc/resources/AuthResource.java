package com.udemy.cursomc.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.cursomc.security.JWTUtil;
import com.udemy.cursomc.security.UserSS;
import com.udemy.cursomc.services.UserService;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	ResponseEntity<Void> refreshToken(HttpServletResponse response){
		
		UserSS user = UserService.authenticated();
		String token = this.jwtUtil.generateToken(user.getUsername());
		
		response.addHeader("Authorization", "Bearer " + token);
		
		return ResponseEntity.noContent().build();
	}

}
