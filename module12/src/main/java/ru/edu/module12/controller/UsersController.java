package ru.edu.module12.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.module12.entity.CustomUser;
import ru.edu.module12.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("user")
@Slf4j
public class UsersController {

    private UserRepository userRepository;

    @Autowired
    public void setRepositories(UserRepository userRepository) {
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
     *
     * @param model model
     * @return thymeleaf html with all users at H2-db
     */
    @PostMapping("getAll")
    public String getAllUsers(Model model) {
        List<CustomUser> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "showAllUsers";
    }

    /**
     * @return thymeleaf html page with added user
     */
    @GetMapping(value = "add")
    public String addNewUser() {
        return "addUser";
    }

    /**
     *
     * @param name person name
     * @param age person age
     * @param model model
     * @return thymeleaf html page
     */
    @PostMapping("add")
    public String addUsers(
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

        CustomUser user = new CustomUser();
        user.setAge(ageInt);
        user.setName(name);

        userRepository.save(user);

        model.addAttribute("name", name);

        return "showAddUsers";
    }

}
