package com.hibernate.mappings.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

@Entity
@Table(name ="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank(message = "required")
    @Size(min = 2,message = "length atleast 2")
    private String name;

    @ManyToOne
    @JoinColumn(name = "rentedTo")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User rentedTo;

    public Book(){}
    public Book(String title){
        this.name = title;
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

    public Optional<User> getRentedTo() {
        return Optional.ofNullable(rentedTo);
    }

    public void setRentedTo(User rentedTo) {
        this.rentedTo = rentedTo;
    }

   /* public Book getMe(){
        return this;
    }*/
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rentedTo=" + rentedTo +
                '}';
    }
}
