package com.assem.blog.service;


import com.assem.blog.dao.RoleRepository;
import com.assem.blog.dao.UserRepository;
import com.assem.blog.dto.RoleDto;
import com.assem.blog.dto.UserDto;
import com.assem.blog.dto.UserWithRoleDto;
import com.assem.blog.entity.Role;
import com.assem.blog.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users
        ) {
            userDtos.add(user.asDTO());
        }

        return userDtos;
    }

    public List<UserWithRoleDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserWithRoleDto> userDtos = new ArrayList<>();
        for (User user : users
        ) {
            UserWithRoleDto temp = new UserWithRoleDto(user.getId()
                    , user.getUsername()
                    , user.getPassword()
                    , user.getBio()
                    , user.getRoles());
            userDtos.add(temp);
        }

        return userDtos;
    }

    public UserDto findById(UUID userId) {
        User user = userRepository.getById(userId);

        return user.asDTO();
    }

    public UserDto getUserByUserName(String username) {
        User user = userRepository.findByUsername(username);

        return user.asDTO();
    }

    public UserDto saveUser(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = User.builder()
                .username(userDto.getUserName())
                .password(encodedPassword)
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


    public RoleDto saveRole(RoleDto roleDto) {
        Role role = Role.builder()
                .name(roleDto.getName())
                .build();

        return roleRepository.save(role).asDto();
    }


    public void assignRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
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
