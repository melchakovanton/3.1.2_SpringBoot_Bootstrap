package com.example.service;

import com.example.model.Role;
import com.example.model.User;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers();

    User findById(Long id);

    void addUser(User user);

    void deleteUser(User user);

    void updateUser(User user);

    User getUserByName(String name);

    Role findByRole(String role);

    Role findByRole(Long id);

    Set<Role> findByRole(Set<Long> roleId);

    List<Role> getAllRoles();
}
