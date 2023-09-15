package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public interface RoleService {
    Set<Role> getAllRoles();
    void saveRole(Role role);
    Role getRoleById(Long id);
    void deleteRoleById(Long id);
    Role getRole(String role);
    void addRolesToUser(User user, Long[] roleId);
}