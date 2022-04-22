package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getUserById(Long id);

    List<User> getAllUsers();

    Optional<User> findByEmail(String email);

    void removeUserById(Long id);

    void saveUser(User user);


}
