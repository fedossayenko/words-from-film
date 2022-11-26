package com.example.wordsfromfilm.services;

import com.example.wordsfromfilm.models.User;
import com.example.wordsfromfilm.repositories.UsersRepository;
import com.example.wordsfromfilm.security.MongoUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MongoAuthUserDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public MongoAuthUserDetailService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public MongoUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUsername(username);

        if (user.isEmpty())  throw new UsernameNotFoundException("User not found.");

        return new MongoUserDetails(user.get());
    }
}
