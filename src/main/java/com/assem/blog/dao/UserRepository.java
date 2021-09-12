package com.assem.blog.dao;

import com.assem.blog.entity.User;
import com.assem.blog.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @NonNull
    default User getById(@NonNull UUID userId) {
        return findById(userId).orElseThrow(RecordNotFoundException::new);
    }

//    @Query("" +
//            "SELECT CASE WHEN COUNT(u) > 0 THEN " +
//            "TRUE ELSE FALSE END " +
//            "FROM USERS u " +
//            "WHERE u.user_name = ?1"
//    )
//     Boolean SelectExistsUserName(String username);

    Optional<User> findByUserName(String username);
}
