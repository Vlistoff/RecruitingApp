package com.vlistoff.recruiting.services;

import com.vlistoff.recruiting.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.vlistoff.recruiting.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(User updatedUser) {
        User user = userRepository.findByUsername(updatedUser.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + updatedUser.getUsername()));

        // Обновление необходимых полей
        if (updatedUser.getRole() != null) {
            user.setRole(updatedUser.getRole());
        }
        if (updatedUser.getSkills() != null) {
            user.setSkills(updatedUser.getSkills());
        }
        if (updatedUser.getEducation() != null) {
            user.setEducation(updatedUser.getEducation());
        }
        if (updatedUser.getResume() != null) {
            user.setResume(updatedUser.getResume());
        }
        if (updatedUser.getExperience() != null) {
            user.setExperience(updatedUser.getExperience());
        }
        if (updatedUser.getCompany() != null) {
            user.setCompany(updatedUser.getCompany());
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
    }
}

