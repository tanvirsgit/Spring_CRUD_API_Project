package com.hibernate.mappings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody User user){
        if(user!=null){
            user.setPassword(encoder.encode(user.getPassword()));
            bookDAO.insertUser(user);

            return new ResponseEntity<User>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrent(){
        return new ResponseEntity(bookDAO.getCurrentUser(),HttpStatus.OK);
    }

    @GetMapping("/users/{id}/books")
    public ResponseEntity<List<Book>> GetALLBooksByUser(@PathVariable int id){
        List<Book> bookList;
        User user = bookDAO.getCurrentUser();
        if(!user.getRole().equals("admin") && user.getId()!=id)
            return new ResponseEntity("Not allowed to view others list",HttpStatus.NOT_ACCEPTABLE);
        try{
            bookList=bookDAO.GetAllBooksByUserId(id);
        }catch (NoSuchElementException e){
            return new ResponseEntity("Not allowed to view others list",HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(bookList,HttpStatus.OK);
    }

    @DeleteMapping("users/delete/{id}")
    public ResponseEntity DeleteUser(@PathVariable int id){

        try{
            User user = bookDAO.getCurrentUser();
            if(!user.getRole().equals("admin"))
                return new ResponseEntity("Only admins can delete users",HttpStatus.BAD_REQUEST);
            else{
                bookDAO.removeUser(id);

            }
        }catch (NoSuchElementException e){
            new ResponseEntity("User doesn't exist",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Deleted user successfully",HttpStatus.OK);
    }
}
