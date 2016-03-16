package com.impaqgroup.controller;

import com.impaqgroup.domain.User;
import com.impaqgroup.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest {

    private MockMvc mockMvc;
    private UserRepository userRepositoryMock = mock(UserRepository.class);

    @Before
    public void setup() {

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController(userRepositoryMock))
                .build();
    }

    @Test
    public void shouldReturnEmptyListWhenCallAll() throws Exception {
        // given
        when(userRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        // when
        ResultActions result = mockMvc.perform(get("/users").accept(APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*]", empty()));
    }

    @Test
    public void shouldReturnTwoUsersWhenCallAll() throws Exception {
        // given
        List<User> users = Arrays.asList(
                new User("name1", "pass1", "detail1"), new User("name2", "pass2", "detail2")
        );

        when(userRepositoryMock.findAll()).thenReturn(users);

        // when
        ResultActions result = mockMvc.perform(get("/users").accept(APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*]", hasSize(users.size())));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        // given
        Long userId = 1L;

        // when
        ResultActions result = mockMvc.perform(delete("/users/{userId}", userId));

        // then
        result.andExpect(status().isOk());
        verify(userRepositoryMock).delete(userId);
    }

    @Test
    public void shouldGetUser() throws Exception {
        // given
        Long userId = 1L;
        User user = new User("name1", "pass1", "detail1");
        user.setId(userId);

        when(userRepositoryMock.findOne(userId)).thenReturn(user);

        // when
        ResultActions result = mockMvc.perform(get("/users/{userId}", userId));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.pass").value(user.getPass()))
                .andExpect(jsonPath("$.details").value(user.getDetails()));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        // given
        // given
        Long userId = 1L;
        User user = new User("name1", "pass1", "detail1");
        user.setId(userId);
        when(userRepositoryMock.findOne(userId)).thenReturn(user);

        String content = "{ \"name\": \"Tom\" }";

        // when
        ResultActions result = mockMvc.perform(put("/users/{userId}", userId)
                .content(content)
                .contentType(APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent());

        verify(userRepositoryMock).save(
                argThat(
                    Matchers.<User>allOf(
                            Matchers.<User>hasProperty("details", equalTo("Updated user")),
                            Matchers.<User>hasProperty("name", equalTo("Tom"))
                    )
                ));
    }

    @Test
    public void shouldAddNewUser() throws Exception {
        // given
        String content = "{ \"id\": 1, \"name\": \"Tom\", \"pass\": \"pass\"\n}";

        // when
        ResultActions result = mockMvc.perform(post("/users").content(content).contentType(APPLICATION_JSON));

        // then
        result.andExpect(status().isCreated());

        verify(userRepositoryMock).save(
                argThat(
                        Matchers.<User>allOf(
                                Matchers.<User>hasProperty("details", is("New user")),
                                Matchers.<User>hasProperty("id", equalTo(1L))
                        )
                )
        );
    }
}