package com.hibernate.mappings.Controller;

import com.hibernate.mappings.Entity.User;
import com.hibernate.mappings.Entity.Book;
import com.hibernate.mappings.DataAccess.BookDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/addUser")
    public ResponseEntity addUser(@Valid @RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        bookDAO.insertUser(user);
        return new ResponseEntity("User added", HttpStatus.CREATED);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrent(){
        return new ResponseEntity(bookDAO.getCurrentUser(),HttpStatus.OK);
    }

    @GetMapping("/users/{id}/books")
    public ResponseEntity GetAllBooksByUser(@PathVariable int id){
        Optional<User> user = bookDAO.getCurrentUser();
        List<ResponseEntity> responseEntity=new ArrayList<>();
        user.ifPresent(user1 -> {
            if(!user1.getRole().equals("admin") && user1.getId()!=id){
                responseEntity.add(new ResponseEntity("Not allowed to view others list",HttpStatus.OK));
            }
            List<Book> list= bookDAO.GetAllBooksByUserId(id);
            responseEntity.add(new ResponseEntity(list,HttpStatus.OK));
        });

        return responseEntity.get(0);
    }

    @DeleteMapping("users/delete/{id}")
    public ResponseEntity DeleteUser(@PathVariable int id){
        bookDAO.removeUser(id);
        return new ResponseEntity("User removed successfully",HttpStatus.OK);
    }
    
}
