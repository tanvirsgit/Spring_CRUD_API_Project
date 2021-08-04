package com.hibernate.mappings;

import com.hibernate.mappings.CustomError.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> errors = new HashMap<>();
        String field,msg;
        for(FieldError error: ex.getBindingResult().getFieldErrors()){
            field= error.getField();
            msg=error.getDefaultMessage();
            errors.put(field,msg);
        }
        return new ResponseEntity(errors,HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handler(MethodArgumentTypeMismatchException e){
        return e.getName()+" is not valid";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchUserFound.class)
    public String handleNoUser(){
        return "No user found with provided id";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoBookFound.class)
    public String handleNoBook(){
        return "No book found with provided id";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyIssued.class)
    public String handleAlreadyIssued(){
        return "This book is already issued";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UnAuthorizedReturn.class)
    public String handleUnAAuthorized(){
        return "You are not authorized";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoRentedByUser.class)
    public String handleNotRented(){
        return "Book is not rented by you";
    }
}
