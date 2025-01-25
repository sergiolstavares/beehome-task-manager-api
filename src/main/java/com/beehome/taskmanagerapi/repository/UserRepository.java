package com.beehome.taskmanagerapi.repository;

import com.beehome.taskmanagerapi.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserModel save(UserModel user);

    @Query("select u from UserModel u where u.email = :email")
    Optional<UserModel> findUserByEmail(@Param("email") String email);
}
