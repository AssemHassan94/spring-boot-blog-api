package com.assem.blog;

import com.assem.blog.dto.RoleDto;
import com.assem.blog.dto.UserDto;
import com.assem.blog.entity.Role;
import com.assem.blog.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new RoleDto(null, "ROLE_USER"));
            userService.saveRole(new RoleDto(null, "ROLE_MANAGER"));
            userService.saveRole(new RoleDto(null, "ROLE_ADMIN"));
            userService.saveRole(new RoleDto(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new UserDto(null, "Assem", "123456", "bio1"));
            userService.saveUser(new UserDto(null, "Ahmed", "123456", "bio2"));
            userService.saveUser(new UserDto(null, "MO", "123456", "bio3"));
            userService.saveUser(new UserDto(null, "MOSalah", "123456", "bio4"));

            userService.assignRoleToUser("Assem", "ROLE_USER");
            userService.assignRoleToUser("Ahmed", "ROLE_SUPER_ADMIN");
            userService.assignRoleToUser("MO", "ROLE_ADMIN");
            userService.assignRoleToUser("MOSalah", "ROLE_MANAGER");

        };
    }

}
