package com.EmailSender.EmailSender.repositorios;

import com.EmailSender.EmailSender.models.UserEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

   /* @Query("SELECT
            email,
            cant_email
            FROM
            users
            WHERE
            cant_email>0;")
    public List<UserEntity> obtenerEmailsYCantEmails();*/

}
