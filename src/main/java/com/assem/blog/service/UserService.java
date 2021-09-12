package com.assem.blog.service;


import com.assem.blog.dao.UserRepository;
import com.assem.blog.dto.UserDto;
import com.assem.blog.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

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
        User user = userRepository.getById(userId);

        return user.asDTO();
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

    public UserDto follow(UUID userId, UUID followerId) {
        User user = userRepository.getById(userId);
        User follower = userRepository.getById(followerId);
        user.followUser(follower);

        return userRepository.save(user).asDTO();
    }

    public UserDto unfollow(UUID userId, UUID toUnfollowId) {
        User user = userRepository.getById(userId);
        User toUnfollow = userRepository.getById(toUnfollowId);
        user.unFollowUser(toUnfollow);

        return userRepository.save(user).asDTO();
    }
}
