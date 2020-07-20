package com.example.controller;

import com.example.model.Role;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.model.User;
import com.example.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/")
public class MainController {


    private UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String login(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/user";
        }
        return "login";
    }

    @GetMapping("user")
    public String user(Authentication authentication, Model model) {
        if (authentication.isAuthenticated()) {
            String userName = authentication.getName();
            User user = userService.getUserByName(userName);
            model.addAttribute("user", user);
        }
        return "index";
    }

    @GetMapping("admin")
    public String showUsersTable(ModelMap model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", userService.getAllRoles());
        return "index";
    }

//    @GetMapping("signup")
//    public String showSignUpForm(User user, ModelMap model) {
//        return "add-user";
//    }

    @PostMapping("adduser")
    public String addUser(@RequestParam(value = "role_id", required = false) Set<Long> roleId,
                          @Validated User user, BindingResult result,
                          ModelMap model) {
//        if (result.hasErrors()) {
//            return "add-user";
//        }
        user.setRoles(userService.findByRole(roleId));
        userService.addUser(user);
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }


//    @GetMapping("edit/{id}")
//    public String showUpdateForm(@PathVariable("id") long id, Model model) {
//        User user = userService.findById(id);
//        model.addAttribute("user", user);
//        return "update-user";
//    }

    @PostMapping("update/{id}")
    public String updateUser(@RequestParam(value = "role_id") Set<Long> roleId,
                             @PathVariable("id") long id, @Validated User user,
                             BindingResult result, Model model, HttpSession session) {
//        if (result.hasErrors()) {
//            user.setId(id);
//            return "update-user";
//        }
        user.setRoles(userService.findByRole(roleId));
        userService.updateUser(user);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model, HttpSession session) {
        User user = userService.findById(id);
        userService.deleteUser(user);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }
}
