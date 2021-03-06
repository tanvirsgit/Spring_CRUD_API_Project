package com.hibernate.mappings.DataAccess;

import com.hibernate.mappings.CustomError.*;
import com.hibernate.mappings.Entity.Book;
import com.hibernate.mappings.Entity.User;
import com.hibernate.mappings.UserDetailServices.MyUserDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class BookDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void rentBook(int bookId){
        Session session = sessionFactory.getCurrentSession();
        Optional<Book> book = GetBookById(bookId);
        if(!book.isPresent()) throw new NoBookFound();
        Optional<User> tempUser = book.flatMap(Book::getRentedTo);
        if(tempUser.isPresent()) throw new AlreadyIssued();
        Optional<User> user = getCurrentUser();
        user.ifPresent(u->u.rentBook(book.get()));
    }

    @Transactional
    public void returnBook(int bookId){
        Session session = sessionFactory.getCurrentSession();
        Optional<Book> book = Optional.ofNullable(session.get(Book.class,bookId));
        if(!book.isPresent()) throw new NoBookFound();
        Optional<User> tempUser = book.flatMap(Book::getRentedTo);
        if(!tempUser.isPresent()) throw new NoRentedByUser();
        Optional<User> user = getCurrentUser();
        List<Book> books = user.map(u-> u.getRentedBooks()).orElse(new ArrayList<>());
        user.ifPresent(u->{
            if(books.contains(book.get()))
                u.returnBook(book.get());
            else
                throw new NoRentedByUser();
        });
    }

    @Transactional
    public Optional<User> GetUserById(int id){
        Session session = sessionFactory.getCurrentSession();
        Optional<User> user =Optional.ofNullable(session.get(User.class,id));
        return user;
    }

    @Transactional
    public Optional<User> GetUserByName(String name){
        Query query = sessionFactory.getCurrentSession().createQuery(
                "from User where name=:name"
        ).setParameter("name",name);

        Optional<User> user= Optional.ofNullable((User) query.uniqueResult());
        return user;
    }

    @Transactional
    public Optional<Book> GetBookById(int id){
        Session session = sessionFactory.getCurrentSession();
        Optional<Book> book = Optional.ofNullable(session.get(Book.class,id));
        return book;
    }

    @Transactional
    public List<Book> GetAllBooksByUserId(int id){
        Optional<User> user = GetUserById(id);
        List<Book> books = user.map(u->u.getRentedBooks()).orElseThrow(NoSuchUserFound::new);
        return books;
    }

    @Transactional
    public List<Book> GetAllAvailableBooks(){
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery("from Book where rentedTo=null").list();
        return books;
    }

    @Transactional
    public void insertBook(Book book){
        sessionFactory.getCurrentSession().persist(book);
    }

    @Transactional
    public void insertUser(User user){
        sessionFactory.getCurrentSession().persist(user);
    }

    @Transactional
    public void removeUser(int id){
        Session session = sessionFactory.getCurrentSession();
        Optional<User> user = GetUserById(id);
        if(!user.isPresent()) throw new NoSuchUserFound();
        user.ifPresent(u->session.remove(u));
    }

    @Transactional
    public Optional<User> getCurrentUser(){
        MyUserDetails userDetails =(MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = GetUserByName(userDetails.getUsername());
        return user;
    }

    @Transactional
    public Book AddOrUpdateBook(Book book,int id){
        Session session= sessionFactory.getCurrentSession();
        Optional<Book> book1 = GetBookById(id);

        return book1.map(b->{
            b.setName(book.getName());
            return b;
        }).orElseGet(()->{
           session.merge(book);
           return book;
        });
    }

}
