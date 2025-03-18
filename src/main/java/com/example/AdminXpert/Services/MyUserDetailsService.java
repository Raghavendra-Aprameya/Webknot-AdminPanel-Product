package com.example.AdminXpert.Services;

import com.example.AdminXpert.Entity.UserPrincipal;
import com.example.AdminXpert.Entity.Users;
import com.example.AdminXpert.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
           Users user=userRepository.findByUsername(username);

           if(user==null)
           {
               System.out.println("User not found");
               throw new UsernameNotFoundException("User Not Found");
           }
           return new UserPrincipal(user);
    }
}
