package com.example.vadim.BootApp.controller;

import com.example.vadim.BootApp.model.User;
import com.example.vadim.BootApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
public class UserController {


    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String welcome() {
        return "pages/welcome";
    }

    @GetMapping("/users")
    public String allUsersPage(Model model) {
        List<User> list = userService.getAllUsers();
        model.addAttribute("users", list);
        return "pages/index";
    }

    @GetMapping("/user")
    public String show(@RequestParam("id") Long id,
                       Model model) {
        User user = userService.getUser(id);

        if (user == null) {
            return "pages/noUser";
        }

        model.addAttribute("user", user);
        return "pages/show";
    }

    @PostMapping("/user")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/new")
    public String addUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "pages/newUser";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("user/edit")
    public String editUser(@RequestParam("id") Long id,
                           Model model) {
        User user = userService.getUser(id);

        if (user == null) {
            return "pages/noUser";
        }

        model.addAttribute("user", user);
        return "pages/edit";
    }

    @PostMapping("/user/edit")
    public String update(@ModelAttribute("user") User user,
                         @ModelAttribute("id") Long id) {
        userService.changeUser(id,user);
        return "redirect:/users";
    }

}
