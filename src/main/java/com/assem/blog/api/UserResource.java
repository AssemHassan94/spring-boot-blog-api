package com.assem.blog.api;


import com.assem.blog.dto.RoleDto;
import com.assem.blog.dto.RoleToUserDTO;
import com.assem.blog.dto.UserDto;
import com.assem.blog.dto.UserWithRoleDto;
import com.assem.blog.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserResource {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {

        return userService.findAll();
    }

    @GetMapping("/roles")
    public List<UserWithRoleDto> getUsersWithRoles() {

        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable UUID userId) {
        return userService.findById(userId);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserDto user = userService.getUserByUserName(username);
                String access_token = JWT.create()
                        .withSubject("username")
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
//                        .withClaim("roles", user.getRoles()
//                                .stream()
//                                .map(Role::getName)
//                                .collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("Refresh token is missing ");
        }
    }

    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }


    @DeleteMapping("/{userId}")
    public void delete(@PathVariable UUID userId) {
        UserDto userDto = userService.findById(userId);
        userService.delete(userId);
    }

    @PostMapping("/roles/save")
    public RoleDto saveRole(@Valid @RequestBody RoleDto roleDto) {
        return userService.saveRole(roleDto);
    }

    @PostMapping("/roles/assign")
    public void assignRoleToUser(@Valid @RequestBody RoleToUserDTO roleToUserDTO) {
        userService.assignRoleToUser(roleToUserDTO.getUsername()
                , roleToUserDTO.getRoleName());
    }

    @PutMapping("/{userId}")
    public UserDto update(@PathVariable UUID userId, @Valid @RequestBody UserDto userDto) {

        return userService.update(userId, userDto);
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
