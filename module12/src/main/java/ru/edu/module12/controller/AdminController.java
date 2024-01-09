package ru.edu.module12.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.module12.entity.CustomUser;
import ru.edu.module12.repository.UserRepository;

@Controller
@RequestMapping("admin")
@Slf4j
public class AdminController {

    private UserRepository userRepository;

    @Autowired
    public void setRepositories(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        CustomUser customUser = userRepository.findCustomUserByName(name);
        if (customUser != null) {
            try {
                userRepository.deleteCustomUserByName(name);
            } catch (IllegalArgumentException ex) {
                return "error";
            }
            model.addAttribute("name", name);
            return "showDeleteUsers";
        } else {
            return "error";
        }
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
            CustomUser user = userRepository.findCustomUserByName(oldName);
            userRepository.updateUserByName(newName, ageInt, user.getName());
        } catch (Exception ex) {
            log.error(ex.getMessage(), (Object) ex.getStackTrace());
            return "error";
        }

        model.addAttribute("oldName", oldName);

        return "showChangeUser";
    }


}
