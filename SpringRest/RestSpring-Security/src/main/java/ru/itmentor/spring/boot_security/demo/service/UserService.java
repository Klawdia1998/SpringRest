package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> findAll();

    User getById(long id);

    void save(User user);

    void deleteById(long id);

    User findByUsername(String username);

    User passwordCoder(User user);

    Set<Role> getRolesByIds(Set<Role> roles);

    void addDefaultUser();
}
