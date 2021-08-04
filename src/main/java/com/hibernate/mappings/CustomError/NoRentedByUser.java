package com.hibernate.mappings.CustomError;

public class NoRentedByUser extends RuntimeException{
    public NoRentedByUser(){
        super();
    }
}
