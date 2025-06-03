package com.danyarov.library.service.impl;

import com.danyarov.library.dao.UserDao;
import com.danyarov.library.exception.InactiveAccountException;
import com.danyarov.library.exception.ServiceException;
import com.danyarov.library.model.User;
import com.danyarov.library.model.UserRole;
import com.danyarov.library.service.PasswordEncoder;
import com.danyarov.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * User service implementation
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User register(User user) {

        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            throw new ServiceException("User with email " + user.getEmail() + " already exists");
        }

        user.setPassword(PasswordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole(UserRole.READER);
        }
        user.setActive(true);

        logger.info("Registering new user: {}", user.getEmail());
        return userDao.save(user);
    }

    @Override
    public Optional<User> authenticate(String email, String password) {
        Optional<User> userOpt = userDao.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (!user.isActive()) {
                logger.warn("Authentication failed for inactive user: {}", email);
                throw new InactiveAccountException();
            }

            if (PasswordEncoder.matches(password, user.getPassword())) {
                logger.info("User authenticated successfully: {}", email);
                return Optional.of(user);
            }
        }

        logger.warn("Authentication failed for user: {}", email);
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return userDao.findByRole(role.getValue());
    }

    @Override
    public User update(User user) {
        Optional<User> existingUser = userDao.findById(user.getId());
        if (existingUser.isEmpty()) {
            throw new ServiceException("User not found with id: " + user.getId());
        }

        // Don't update password if not provided
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(existingUser.get().getPassword());
        } else {
            user.setPassword(PasswordEncoder.encode(user.getPassword()));
        }

        logger.info("Updating user: {}", user.getEmail());
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Deleting user with id: {}", id);
        return userDao.deleteById(id);
    }

    @Override
    public User toggleActiveStatus(Long id) {
        Optional<User> userOpt = userDao.findById(id);
        if (userOpt.isEmpty()) {
            throw new ServiceException("User not found with id: " + id);
        }

        User user = userOpt.get();
        user.setActive(!user.isActive());

        logger.info("Toggling active status for user: {}", user.getEmail());
        return userDao.update(user);
    }
}
