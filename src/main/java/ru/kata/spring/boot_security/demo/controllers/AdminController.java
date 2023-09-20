package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAllUsersController(Principal principal, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "adminPage";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "admin/edit")
    public String edit(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "userInAdmin";
    }

    @GetMapping("/add")
    public String add(Principal principal, Model model) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "add";
    }

    @PostMapping("/{id}/edit")
    public String editUser(@ModelAttribute("user") User user,
                           @RequestParam(required = false) String roleAdmin) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.getRole("USER"));
        if (roleAdmin != null && roleAdmin.equals("ADMIN")) {
            roles.add(roleService.getRole("ADMIN"));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(required = false) String roleAdmin) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.getRole("USER"));
        if (roleAdmin != null && roleAdmin.equals("ADMIN")) {
            roles.add(roleService.getRole("ADMIN"));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String showUserInfo(Principal principal, Model model) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "userInAdmin";
    }
}