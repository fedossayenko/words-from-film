package com.example.wordsfromfilm.repositories;

import com.example.wordsfromfilm.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
