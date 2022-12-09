package com.self.company.controller;

import com.self.company.model.User;
import com.self.company.service.UserService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User findById(@PathVariable @NotBlank(message = "Id could not be blank") String id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @NotBlank(message = "Id could not be blank") String id) {
        userService.deleteById(id);
    }

}
