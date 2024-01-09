package ru.edu.module12.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.edu.module12.entity.CustomUser;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    static CustomUser user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;
    @BeforeAll
    static void initUser() {
        user = new CustomUser(1L, "vasya", 26);
    }

    @Test
    @DisplayName("Test save user to db")
    void saveUser() {
        CustomUser u = userRepository.save(user);
        assertThat(testEntityManager.find(CustomUser.class, u.getId())).isEqualTo(u);
    }

    @Test
    @DisplayName("Delete User from Db")
    void deleteUserByName() {
        userRepository.save(user);
        userRepository.deleteCustomUserByName(user.getName());
        assertThat(testEntityManager.find(CustomUser.class, user.getId())).isNull();
    }

    @Test
    @DisplayName("Find user by name")
    void findUserByName() {
        userRepository.save(user);
        CustomUser u = userRepository.findCustomUserByName(user.getName());
        assertThat(testEntityManager.find(CustomUser.class, u.getId())).isEqualTo(u);
    }

    @Test
    @DisplayName("Change user data")
    void changeUserData() {
        userRepository.save(user);
        CustomUser newUser = new CustomUser(1L, "Valya",49);
        userRepository.updateUserByName(newUser.getName(), newUser.getAge(), user.getName());
        assertThat(testEntityManager.find(CustomUser.class, user.getId())).isEqualTo(user);
    }

}
