package com.example.wordsfromfilm.services;

import com.example.wordsfromfilm.models.User;
import com.example.wordsfromfilm.repositories.UsersRepository;
import com.example.wordsfromfilm.security.MongoUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MongoAuthUserDetailService {

    private final UsersRepository usersRepository;

    @Autowired
    public MongoAuthUserDetailService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public MongoUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return new MongoUserDetails(user);
    }
}
