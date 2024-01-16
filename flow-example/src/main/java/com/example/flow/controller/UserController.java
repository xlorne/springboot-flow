package com.example.flow.controller;

import com.codingapi.springboot.framework.dto.response.MultiResponse;
import com.example.flow.domain.User;
import com.example.flow.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/list")
    public MultiResponse<User> list() {
        return MultiResponse.of(userRepository.findAll());
    }

}
