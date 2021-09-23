package com.assem.blog.dao;

import com.assem.blog.entity.Role;
import com.assem.blog.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    @NonNull
    default Role getById(@NonNull UUID roleId) {
        return findById(roleId).orElseThrow(RecordNotFoundException::new);
    }

    Role findByName(String name);
}
