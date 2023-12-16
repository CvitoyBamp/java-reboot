package com.example.module11.repository;

import com.example.module11.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteUserByName(String name) throws IllegalArgumentException;

    @Modifying
    @Query(value = "UPDATE public.users SET name = :newName, age = :newAge where name = :oldName", nativeQuery = true)
    void updateUserByName(@Param("newName") String newName, @Param("newAge") Integer newAge, @Param("oldName") String name) throws IllegalArgumentException;

    User findUserByName(String name);
}
