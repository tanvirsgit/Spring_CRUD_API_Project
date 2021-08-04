package com.hibernate.mappings.Controller;

import com.hibernate.mappings.Entity.Book;
import com.hibernate.mappings.DataAccess.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookLoanController {

    @Autowired
    private BookDAO bookDAO;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> allBooks(){
        return new ResponseEntity(bookDAO.GetAllAvailableBooks(), HttpStatus.OK);
    }

    @GetMapping("/books/rent/{id}")
    public ResponseEntity rentBook(@Valid @PathVariable int id){
        bookDAO.rentBook(id);
        return new ResponseEntity("Book rented successfully",HttpStatus.OK);
    }

    @GetMapping("/books/return/{id}")
    public ResponseEntity returnBook(@Valid @PathVariable int id){
        bookDAO.returnBook(id);
        return new ResponseEntity("Book returned successfully",HttpStatus.OK);
    }

    @PutMapping("books/edit/{id}")
    public ResponseEntity EditOrAddBook(@Valid @PathVariable int id,@Valid @RequestBody Book book){
        Book book1= bookDAO.AddOrUpdateBook(book,id);
        return new ResponseEntity("Updated Successfully",HttpStatus.OK);
    }
}
