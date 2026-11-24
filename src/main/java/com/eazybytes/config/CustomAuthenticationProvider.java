package com.eazybytes.config;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final CustomUserDetailsService customUserDetailsService;
  private final PasswordEncoder encoder;

  @Override
  public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {

      UserDetails userdetails =  customUserDetailsService.loadUserByUsername(authentication.getName());
      if(encoder.matches(authentication.getCredentials().toString(), userdetails.getPassword())){
        return new UsernamePasswordAuthenticationToken(authentication.getName(), userdetails.getPassword(), userdetails.getAuthorities());
      }else{
        throw new BadCredentialsException("Invalid Password");
      }

  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
  

}
