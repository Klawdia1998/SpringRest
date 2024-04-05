package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.dao.RoleDao;
import ru.itmentor.spring.boot_security.demo.dao.UserDao;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public Set<Role> getRolesByIds(Set<Role> roles) {
        List<Long> roleIds = roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
        return roleService.findByIdRoles(roleIds);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User getById(long id) {
        return userDao.getById(id);
    }

    @Override
    @Transactional
    public void save(User user) {
        userDao.save(passwordCoder(user));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        userDao.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @PostConstruct
    @Transactional
    public void addDefaultUser() {
        Set<Role> roles1 = new HashSet<>();
        roles1.add(roleDao.findById(1L).orElse(null));
        Set<Role> roles2 = new HashSet<>();
        roles2.add(roleDao.findById(1L).orElse(null));
        roles2.add(roleDao.findById(2L).orElse(null));
        User user1 = new User("Klawdia", "Kolo", (byte) 26, "k.kolo@mail.com", "user", "user", roles1);
        User user2 = new User("Admin", "Admin", (byte) 30, "admin@mail.com", "admin", "admin", roles2);
        save(user1);
        save(user2);
    }
}
