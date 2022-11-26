package com.example.wordsfromfilm.services;


import com.example.wordsfromfilm.WordsFromFilmApplication;
import com.example.wordsfromfilm.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {WordsFromFilmApplication.class})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MongoAuthUserDetailServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MongoTemplate mongoTemplate;

    private MockMvc mvc;

    private static final String USER_NAME = "username";
    private static final String NOT_EXISTING_USER_NAME = "not_existing_user";
    private static final String PASSWORD = "password";
    private static final String WRONG_PASSWORD = "wrong_password";
    private static final String USER_API = "/api/users/index";

    @BeforeEach
    public void setup() {

        setUp();

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    private void setUp() {
        User user = new User();
        user.setUsername(USER_NAME);
        user.setPassword(PASSWORD);
        mongoTemplate.save(user);
    }

    @Test
    void givenUserCredentials_whenInvokeUserAuthorizedEndPoint_thenReturn200() throws Exception {
        mvc.perform(get(USER_API).with(httpBasic(USER_NAME, PASSWORD)))
                .andExpect(status().isOk());
    }

    @Test
    void givenUserNotExists_whenInvokeEndPoint_thenReturn401() throws Exception {
        mvc.perform(get(USER_API).with(httpBasic(NOT_EXISTING_USER_NAME, PASSWORD)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void givenUserExistsAndWrongPassword_whenInvokeEndPoint_thenReturn401() throws Exception {
        mvc.perform(get(USER_API).with(httpBasic(USER_NAME, WRONG_PASSWORD)))
                .andExpect(status().isUnauthorized());
    }
}