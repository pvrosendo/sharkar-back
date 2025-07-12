package com.rosendo.sharkar.domain.repository;

import com.rosendo.sharkar.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserName(String username);
    User findUserByEmail(String email);

}
