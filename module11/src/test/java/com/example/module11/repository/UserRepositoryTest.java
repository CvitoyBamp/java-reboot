package com.example.module11.repository;

import com.example.module11.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    static User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;
    @BeforeAll
    static void initUser() {
        user = new User(1L, "vasya", 26);
    }

    @Test
    @DisplayName("Test save user to db")
    void saveUser() {
        User u = userRepository.save(user);
        assertThat(testEntityManager.find(User.class, u.getId())).isEqualTo(u);
    }

    @Test
    @DisplayName("Delete User from Db")
    void deleteUserByName() {
        userRepository.save(user);
        userRepository.deleteUserByName(user.getName());
        assertThat(testEntityManager.find(User.class, user.getId())).isNull();
    }

    @Test
    @DisplayName("Find user by name")
    void findUserByName() {
        userRepository.save(user);
        User u = userRepository.findUserByName(user.getName());
        assertThat(testEntityManager.find(User.class, u.getId())).isEqualTo(u);
    }

    @Test
    @DisplayName("Change user data")
    void changeUserData() {
        userRepository.save(user);
        User newUser = new User(1L, "Valya",49);
        userRepository.updateUserByName(newUser.getName(), newUser.getAge(), user.getName());
        assertThat(testEntityManager.find(User.class, 1L)).isEqualTo(user);
    }

}
