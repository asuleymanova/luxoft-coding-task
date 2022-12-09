package com.self.company.controller;

import com.self.company.model.User;
import com.self.company.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final String USER_ID = "user-id";
    private static final String EMPTY = " ";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Test
    public void givenId_whenFindUserById_thenReturnUser() throws Exception {

        User user = User.builder()
                .id("c5cba239-8f3a-4db3-adf1-b1601ad08b9a")
                .name("Artur")
                .updateTimeStamp(LocalDateTime.now())
                .description("Test Description")
                .build();

        when(service.findById(anyString())).thenReturn(user);

        mvc.perform(get("/users/{id}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.id", is(user.getId())));

    }

    @Test
    public void givenBlankId_whenFindUserById_thenReturnBadRequest() throws Exception {

        User user = User.builder()
                .id("c5cba239-8f3a-4db3-adf1-b1601ad08b9a")
                .name("Artur")
                .updateTimeStamp(LocalDateTime.now())
                .description("Test Description")
                .build();

        when(service.findById(anyString())).thenReturn(user);

        mvc.perform(get("/users/{id}", EMPTY)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Constraint Violations")))
                .andExpect(jsonPath("$.errors[0]", is("findById.id: Id could not be blank")));

        verify(service, times(0)).findById(anyString());


    }

    @Test
    public void givenId_whenDeleteUserById_thenReturnSuccess() throws Exception {

        mvc.perform(delete("/users/{id}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteById(anyString());

    }

    @Test
    public void givenBlankId_whenDeleteUserById_thenReturnBadRequest() throws Exception {

        mvc.perform(delete("/users/{id}", EMPTY)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Constraint Violations")))
                .andExpect(jsonPath("$.errors[0]", is("deleteById.id: Id could not be blank")));

        verify(service, times(0)).deleteById(anyString());

    }
}
