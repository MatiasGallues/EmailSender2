package com.EmailSender.EmailSender.repositorios;

import com.EmailSender.EmailSender.models.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

}
