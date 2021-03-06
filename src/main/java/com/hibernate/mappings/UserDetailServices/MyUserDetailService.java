package com.hibernate.mappings.UserDetailServices;

import com.hibernate.mappings.DataAccess.BookDAO;
import com.hibernate.mappings.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private BookDAO bookDAO;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user= bookDAO.GetUserByName(s);
        return new MyUserDetails(user.orElse(null));
    }
}
