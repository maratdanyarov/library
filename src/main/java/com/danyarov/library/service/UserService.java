package com.danyarov.library.service;

import com.danyarov.library.model.User;
import com.danyarov.library.model.UserRole;

import java.util.List;
import java.util.Optional;

/**
 * User service interface
 */
public interface UserService {
    /**
     * Register new user
     * @param user user to register
     * @return registered user
     */
    User register(User user);

    /**
     * Authenticate user
     * @param email user email
     * @param password user password
     * @return authenticated user or empty if authentication failed
     */
    Optional<User> authenticate(String email, String password);

    /**
     * Find user by ID
     * @param id user ID
     * @return user or empty if not found
     */
    Optional<User> findById(Long id);

    /**
     * Find user by email
     * @param email user email
     * @return user or empty if not found
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
    List<User> findByRole(UserRole role);

    /**
     * Update user
     * @param user user to update
     * @return updated user
     */
    User update(User user);

    /**
     * Delete user
     * @param id user ID
     * @return true if deleted, false otherwise
     */
    boolean delete(Long id);

    /**
     * Toggle user active status
     * @param id user ID
     * @return updated user
     */
    User toggleActiveStatus(Long id);
}
