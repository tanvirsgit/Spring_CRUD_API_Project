package com.hibernate.mappings;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DemoData {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void populateDB(){
        List<User> users = new ArrayList<>();

        User user = new User("Green");
        user.setPassword(passwordEncoder.encode("123"));
        user.setRole("user");
        users.add(user);
        user = new User("Blue");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRole("user");
        users.add(user);
        user = new User("Black");
        user.setPassword(passwordEncoder.encode("12345"));
        user.setRole("admin");
        users.add(user);

        final List<Book> books = new ArrayList<>();
        books.add(new Book("Homo sapiens"));
        books.add(new Book("Wealth of nations"));
        books.add(new Book("Power of habit"));
        books.add(new Book("The great partition"));
        books.add(new Book("The Godfather"));
        System.out.println("Populating data");

        users.forEach(u-> bookDAO.insertUser(u));
        books.forEach(book-> bookDAO.insertBook(book));
    }
}
