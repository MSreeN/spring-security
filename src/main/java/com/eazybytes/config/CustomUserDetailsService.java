package com.eazybytes.config;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.eazybytes.model.Customer;
import com.eazybytes.repo.CustomerRepo;

import lombok.RequiredArgsConstructor;


@Component 
@RequiredArgsConstructor 
public class CustomUserDetailsService implements UserDetailsService{

  final CustomerRepo customerRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Customer customer = customerRepo.findByEmail(username).orElseThrow( () -> new UsernameNotFoundException("User not found"));
    User user = new User(customer.getEmail(), customer.getPwd(), List.of(new SimpleGrantedAuthority(customer.getRole())));
    return user;
  }
  
}
