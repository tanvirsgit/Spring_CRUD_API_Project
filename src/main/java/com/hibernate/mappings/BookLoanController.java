package com.hibernate.mappings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity EditOrAddBook(@PathVariable int id,@RequestBody Book book){
        Book book1= bookDAO.AddOrUpdateBook(book,id);
        return new ResponseEntity("Updated Successfully",HttpStatus.OK);
    }

}
