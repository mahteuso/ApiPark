package com.mateus.park_api.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class ErrorMessage {

    private String path;

    private String method;

    private Integer status;

    private String statusText;

    private String message;

    private Map<String, String> errors;


    public ErrorMessage() {
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message){
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result){
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
        addErrors(result);
    }

    private void addErrors(BindingResult result){
        this.errors = new HashMap<>();
        for (ObjectError fieldError : result.getAllErrors()){
            this.errors.put(fieldError.getObjectName(), fieldError.getDefaultMessage());
        }
    }

}
