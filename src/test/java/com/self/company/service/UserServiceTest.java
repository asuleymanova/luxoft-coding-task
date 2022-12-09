package com.self.company.service;

import com.self.company.exception.ResourceNotFoundException;
import com.self.company.model.User;
import com.self.company.repository.UserRepository;
import com.self.company.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final String USER_ID = "c5cba239-8f3a-4db3-adf1-b1601ad08b9a";
    UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl(userRepository);
    }


    @Test
    void givenValidId_whenFindById_thenShouldReturnValidUser() {
        User user = User.builder()
                .id("c5cba239-8f3a-4db3-adf1-b1601ad08b9a")
                .name("Artur")
                .updateTimeStamp(LocalDateTime.now())
                .description("Test Description")
                .build();
        when(userRepository.findById(anyString())).thenReturn(Optional.ofNullable(user));

        assertThat(userService.findById(USER_ID)).isEqualTo(user);
    }

    @Test
    public void shouldReturnExceptionIfUserIsNotFound() {
        when(userRepository.findById(anyString())).thenThrow(new ResourceNotFoundException("User not found with id = " + USER_ID));
        try {
            userService.findById(USER_ID);
            fail();
        } catch (ResourceNotFoundException e) {
            assertEquals("User not found with id = " + USER_ID, e.getMessage());
        }
    }

    @Test
    public void shouldDeleteUser() {
        userService.deleteById(USER_ID);
        verify(userRepository, times(1)).deleteById(USER_ID);
    }

}
