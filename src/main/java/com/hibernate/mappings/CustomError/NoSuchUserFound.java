package com.hibernate.mappings.CustomError;

public class NoSuchUserFound extends RuntimeException{

    public NoSuchUserFound(){
        super();
    }
}
