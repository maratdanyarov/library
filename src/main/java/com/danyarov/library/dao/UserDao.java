package com.danyarov.library.dao;

import com.danyarov.library.model.User;

import java.util.List;
import java.util.Optional;

/**
 * User DAO interface
 */
public interface UserDao {
    /**
     * Find user by ID
     * @param id user ID
     * @return Optional containing user if found
     */
    Optional<User> findById(Long id);

    /**
     * Find user by email
     * @param email user email
     * @return Optional containing user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find all users
     * @return list of all users
     */
    List<User> findAll();

    /**
     * Find users by role
     * @param role user role
     * @return list of users with specified role
     */
    List<User> findByRole(String role);

    /**
     * Save new user
     * @param user user to save
     * @return saved user with generated ID
     */
    User save(User user);

    /**
     * Update existing user
     * @param user user to update
     * @return updated user
     */
    User update(User user);

    /**
     * Delete user by ID
     * @param id user ID
     * @return true if deleted, false otherwise
     */
    boolean deleteById(Long id);
}
