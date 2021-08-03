package com.hibernate.mappings.Controller;

import com.hibernate.mappings.Entity.User;
import com.hibernate.mappings.Entity.Book;
import com.hibernate.mappings.DataAccess.BookDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/addUser")
    public ResponseEntity addUser(@Valid @RequestBody User user){
        System.out.println("Inside addUser");
        user.setPassword(encoder.encode(user.getPassword()));
        bookDAO.insertUser(user);
        return new ResponseEntity("User added", HttpStatus.CREATED);
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
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleError(MethodArgumentNotValidException ex){
        System.out.println("Indside handleError");
        Map<String,String> errors = new HashMap<>();
        String field,msg;
        for(FieldError error: ex.getBindingResult().getFieldErrors()){
            field= error.getField();
            msg=error.getDefaultMessage();
            errors.put(field,msg);
        }
        return errors;
    }
    
}
