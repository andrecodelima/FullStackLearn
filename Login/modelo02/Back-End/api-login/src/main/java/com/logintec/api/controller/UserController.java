package com.logintec.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping
	public ResponseEntity<String> getUser(){
		return ResponseEntity.ok("Acesso Autorizado");
	}
	
}
//
//eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb2dpbi1hdXRoLWFwaSIsInN1YiI6ImFuYUBpZy5jb20uYnIiLCJleHAiOjE3NDA1MTMwMTF9.43JZy82_7ZJAoCRzs_xavHB381nEcQZ2Ph5kVijLMag
//eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb2dpbi1hdXRoLWFwaSIsInN1YiI6ImFuYUBpZy5jb20uYnIiLCJleHAiOjE3NDA1MTMwMTF9.43JZy82_7ZJAoCRzs_xavHB381nEcQZ2Ph5kVijLMag