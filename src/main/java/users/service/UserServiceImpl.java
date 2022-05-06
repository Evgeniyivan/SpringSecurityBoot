package users.service;

import users.dao.UserDao;
import users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void UserServiceImp(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }
    @Override
    public void update(User updatedUser) {
        User user = getById(updatedUser.getId());
        if (!user.getPassword().equals(userDao.getById(user.getId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.update(updatedUser);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public User getByLogin(String email) {
        return userDao.getByLogin(email);
    }
}