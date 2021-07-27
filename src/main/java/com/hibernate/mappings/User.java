package com.hibernate.mappings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String role;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "rentedTo",cascade = {CascadeType.PERSIST,CascadeType.MERGE}) // default lazy loading
    private List<Book> rentedBooks = new ArrayList<>();

    public User(){}

    public User(String name){
        this.name = name;
    }

    public void rentBook(Book book){
        book.setRentedTo(this);
        rentedBooks.add(book);
    }

    public void returnBook(Book book){
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
