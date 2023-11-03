package com.EmailSender.EmailSender.repositorios;

import com.EmailSender.EmailSender.models.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findAllByCantEmailIsNotNull();

    @Query(value = "UPDATE UserEntity SET cantEmail = 0")
    Void ponerEncero();

}
