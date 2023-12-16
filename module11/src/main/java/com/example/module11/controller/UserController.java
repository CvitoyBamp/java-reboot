package com.example.module11.controller;

import com.example.module11.entity.User;
import com.example.module11.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "user")
@Slf4j
public class UserController {

    // имплементим Jpa-репозиторий
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get all users
     * @return ui for getting users
     */
    @GetMapping(value = "getAll")
    public String getAllUsers() {
        return "allUsers";
    }

    /**
     * Get all users from db
     * @param model model
     * @return all users from db
     */
    @PostMapping(value = "getAll")
    public String getAllUsers(Model model) {
        List<User> userList = userRepository.findAll();
        log.info(userList.toString());
        model.addAttribute("users", userList);
        return "showAllUsers";
    }

    /**
     * ui for adding new users
     * @return page for adding new users
     */
    @GetMapping(value = "add")
    public String addNewUser() {
        return "addUser";
    }

    /**
     * Adding new user from form
     * @param name name of user
     * @param age age of user
     * @param model model
     * @return name of new user and adding him to db
     */
    @PostMapping(value = "add")
    public String addNewUser(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "age") String age,
            Model model
    ) {
        int ageInt = 0;
        try {
            ageInt = Integer.parseInt(age);
        } catch (NumberFormatException nfe) {
            return "error";
        }

        User user = new User();

        user.setAge(ageInt);
        user.setName(name);

        userRepository.save(user);

        model.addAttribute("name", name);

        return "showAddUsers";
    }

    /**
     * ui for deleting user
     * @return page with form for deleting
     */
    @GetMapping(value = "delete")
    public String deleteUser() {
        return "deleteUser";
    }

    /**
     * Deleting user by name
     * @param name name of deleting person
     * @param model model
     * @return name of deleted user
     */
    @PostMapping(value = "delete")
    public String deleteUser(
            @RequestParam(value = "name") String name,
            Model model
    ) {
        try {
            userRepository.deleteUserByName(name);
        } catch (IllegalArgumentException ex) {
            return "error";
        }
        model.addAttribute("name", name);
        return "showDeleteUsers";
    }

    /**
     * ui for change user info
     * @return page with form for changing user data
     */
    @GetMapping(value = "change")
    public String changeUser() {
        return "changeUser";
    }

    /**
     * Changing user data
     * @param oldName existing name of user
     * @param newName new name for user
     * @param newAge new age of user
     * @param model model
     * @return new name of user
     */
    @PostMapping(value = "change")
    public String changeUser(
            @RequestParam(value = "oldName") String oldName,
            @RequestParam(value = "newName") String newName,
            @RequestParam(value = "newAge") String newAge,
            Model model
    ) {
        int ageInt = 0;
        try {
            ageInt = Integer.parseInt(newAge);
            User user = userRepository.findUserByName(oldName);
            userRepository.updateUserByName(newName, ageInt, user.getName());
        } catch (Exception ex) {
            return "error";
        }

        model.addAttribute("oldName", oldName);

        return "showChangeUser";
    }
}
