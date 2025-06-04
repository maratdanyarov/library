package com.danyarov.library.dao;

import com.danyarov.library.model.User;

import java.util.List;
import java.util.Optional;

/**
 * User DAO interface
 */
public interface UserDao extends BasicDao<User, Long> {

    /**
     * Find user by email
     * @param email user email
     * @return Optional containing user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find users by role
     * @param role user role
     * @return list of users with specified role
     */
    List<User> findByRole(String role);
}