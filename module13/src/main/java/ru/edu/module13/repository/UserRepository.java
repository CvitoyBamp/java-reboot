package ru.edu.module13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.module13.entity.CustomUser;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {

    @Transactional
    void deleteCustomUserByName(String name);

    @Transactional(readOnly = true)
    CustomUser findCustomUserByName(String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE public.users SET name = :newName, age = :newAge where name = :oldName", nativeQuery = true)
    void updateUserByName(@Param("newName") String newName, @Param("newAge") Integer newAge, @Param("oldName") String name) throws IllegalArgumentException;

}
