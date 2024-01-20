package ru.edu.module12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.module12.entity.CustomUser;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<CustomUser, Long> {

    void deleteCustomUserByName(String name);

    CustomUser findCustomUserByName(String name);

    @Modifying
    @Query(value = "UPDATE public.users SET name = :newName, age = :newAge where name = :oldName", nativeQuery = true)
    void updateUserByName(@Param("newName") String newName, @Param("newAge") Integer newAge, @Param("oldName") String name) throws IllegalArgumentException;

}
