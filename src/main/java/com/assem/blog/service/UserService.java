package com.assem.blog.service;


import com.assem.blog.dao.UserRepository;
import com.assem.blog.dto.ArticleDto;
import com.assem.blog.dto.UserDto;
import com.assem.blog.entity.Article;
import com.assem.blog.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users
        ) {
            userDtos.add(user.asDTO());
        }
        return userDtos;
    }

    public UserDto findById(UUID userId) {
        return userRepository.findById(userId).get().asDTO();
    }

    public UserDto create(UserDto userDto) {
        User user = User.builder()
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .bio(userDto.getBio())
                .build();

        return userRepository.save(user).asDTO();
    }

    public UserDto update(UUID userId, UserDto userDto) {
        User user = userRepository.getById(userId);
        user.update(userDto.getUserName(), userDto.getPassword(), userDto.getBio());

        return userRepository.save(user).asDTO();
    }

    public void delete(UUID userId) {

        userRepository.delete(userRepository.getById(userId));
    }
}
