package com.vlistoff.recruiting.services;

import com.vlistoff.recruiting.entity.User;

public interface UserService {
    User registerUser(User user);

    User findById(Long id);

    User updateUser(User updatedUser);

    void deleteUser(Long id);

    User findByUsername(String username);
}
