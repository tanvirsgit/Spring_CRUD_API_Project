package com.hibernate.mappings.Controller;

import com.hibernate.mappings.Entity.Book;
import com.hibernate.mappings.DataAccess.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class BookLoanController {

    @Autowired
    private BookDAO bookDAO;


    @GetMapping("/books")
    public ResponseEntity<List<Book>> allBooks(){
        return new ResponseEntity(bookDAO.GetAllAvailableBooks(), HttpStatus.OK);
    }

    @GetMapping("/books/rent/{id}")
    public ResponseEntity rentBook(@PathVariable int id){
        try{
            bookDAO.rentBook(id);
        }catch (Exception e){
            if(e instanceof NoSuchElementException)
                return new ResponseEntity("No book found",HttpStatus.NOT_FOUND);
            else if (e instanceof UnsupportedOperationException)
                return new ResponseEntity("Book is unavailable for rent",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Book rented successfully",HttpStatus.OK);
    }

    @GetMapping("/books/return/{id}")
    public ResponseEntity returnBook(@PathVariable int id){

        try{
            bookDAO.returnBook(id);
        }catch (Exception e){
            if(e instanceof UnsupportedOperationException){
                return new ResponseEntity("This book is not rented by you",HttpStatus.BAD_REQUEST);
            }
            else if( e instanceof NoSuchElementException){
                return new ResponseEntity("No Such Book",HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("Book returned successfully",HttpStatus.OK);
    }

    @PutMapping("books/edit/{id}")
    public ResponseEntity EditOrAddBook(@PathVariable int id,@Valid @RequestBody Book book){
        book.setName(book.getName());
        Book book1= bookDAO.AddOrUpdateBook(book,id);
        return new ResponseEntity("Updated Successfully",HttpStatus.OK);
    }

    String trimmer (String s){
        if(s.length()==0) return s;
        String ans="";
        char previous='\0', current;
        for(int i=0;i<s.length();i++){
            current=s.charAt(i);
            if(current==' ' && previous =='\0') continue;
            else if( current==' ' && previous==' ') continue;
            else if(current==' ' && previous!=' '){
                previous=current;
            }
            else if(current!=' ' && previous==' '){
                ans+=previous;
                ans+=current;
                previous=current;
            }
            else{
                ans+=current;
                previous=current;
            }
        }
        System.out.println("|"+ans+"|");
        return ans;
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
