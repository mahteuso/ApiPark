package com.mateus.park_api.exception;

public class NotFoundClientId extends RuntimeException{

    public NotFoundClientId(String message){
        super(message);
    }
}
