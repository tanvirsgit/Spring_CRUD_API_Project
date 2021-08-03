package com.hibernate.mappings.Entity;

import com.hibernate.mappings.Entity.Book;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "required")
    @Column
    private String name;

    @NotBlank(message = "required")
    @Size(min = 3, message = "Password length at least 3")
    @Column
    private String password;

    @NotBlank(message = "required")
    @Pattern(regexp = "user|admin", message = "enter valid type, only user and admin are accepted")
    @Column
    private String role;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rentedTo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // default lazy loading
    private List<Book> rentedBooks = new ArrayList<>();

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public void rentBook(Book book) {
        book.setRentedTo(this);
        rentedBooks.add(book);
    }

    public void returnBook(Book book) {
        rentedBooks.remove(book);
        book.setRentedTo(null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getRentedBooks() {
        return rentedBooks;
    }

    public void setRentedBooks(List<Book> rentedBooks) {
        this.rentedBooks = rentedBooks;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rentedBooks=" + rentedBooks.isEmpty() +
                '}';
    }
}
