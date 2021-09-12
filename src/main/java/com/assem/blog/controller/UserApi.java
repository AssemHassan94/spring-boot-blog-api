package com.assem.blog.controller;


import com.assem.blog.dto.UserDto;
import com.assem.blog.exception.RecordNotFoundException;
import com.assem.blog.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Slf4j
@AllArgsConstructor
public class UserApi {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {

        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable UUID userId) {
        return userService.findById(userId);
    }

    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {

        return userService.create(userDto);
    }

    @PutMapping("/{userId}")
    public UserDto update(@PathVariable UUID userId, @Valid @RequestBody UserDto userDto) {

        return userService.update(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable UUID userId) {
        UserDto userDto = userService.findById(userId);
        userService.delete(userId);
    }

//    @PostMapping("/{userId}/followers/{followerId}")
//    public UserDto followUser(@PathVariable UUID userId, @PathVariable UUID followerId) {
//        return userService.follow(userId, followerId);
//    }
//
//    @DeleteMapping("/{userId}/followers/{followerId}")
//    public UserDto UnfollowUser(@PathVariable UUID userId, @PathVariable UUID followerId) {
//        return userService.unfollow(userId, followerId);
//    }
}
