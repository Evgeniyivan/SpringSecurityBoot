package users.service;

import users.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


public class UserDetailServiceImpl {
    @Service
    public static class UserDetailsServiceImpl implements UserDetailsService {

        private final UserDao userDao;

        @Autowired
        public UserDetailsServiceImpl(UserDao userDao) {
            this. userDao =  userDao;
        }

        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            return userDao.getByLogin(s);
        }
    }
}
