package com.example.wordsfromfilm.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
@AllArgsConstructor
public class User {

    @Id
    private ObjectId id;
    private String username;
    private String name;
    private String password;
}
