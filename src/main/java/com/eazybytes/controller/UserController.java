package com.eazybytes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.model.Customer;
import com.eazybytes.repo.CustomerRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
public class UserController {

  private final CustomerRepo customerRepo;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/register")
  ResponseEntity<String> registerCustomer(@RequestBody Customer customer) {
    try { 
      customer.setPwd(passwordEncoder.encode(customer.getPwd()));
      Customer savedCustomer = customerRepo.save(customer);
      return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered");

    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while creating a new user " + ex.getMessage());
    }
  }
}
