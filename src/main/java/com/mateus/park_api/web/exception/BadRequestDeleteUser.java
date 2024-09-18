package com.mateus.park_api.web.exception;

public class BadRequestDeleteUser extends RuntimeException{
    public BadRequestDeleteUser(String message){
        super(message);
    }
}
