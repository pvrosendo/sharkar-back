package com.rosendo.dream_car.domain.service;

import com.rosendo.dream_car.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        var user = repository.findByUsername(usernameOrEmail);

        if (user == null) {
            user = repository.findByEmail(usernameOrEmail);
        }
        System.out.println(user);

        if (user != null) return user;
        else throw new UsernameNotFoundException("Username or email "+ usernameOrEmail +" not found!");
    }
}
