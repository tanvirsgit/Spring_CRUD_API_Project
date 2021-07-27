package com.hibernate.mappings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private BookDAO bookDAO;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user= bookDAO.GetUserByName(s);
        return new MyUserDetails(user);
    }
}
