package com.mateus.park_api.web.exception;

import com.mateus.park_api.exception.NotFoundClientId;
import com.mateus.park_api.exception.PasswordInvalidException;
import com.mateus.park_api.exception.UserNameIntegrityViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                        HttpServletRequest request, BindingResult result) {
        log.error("Api error - ", ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) inválido(s)", result));
    }

    @ExceptionHandler(UserNameIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> dataIntegrityViolationException(UserNameIntegrityViolationException dt,
                                                                        HttpServletRequest request) {
        log.error("Api error - ", dt);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, dt.getMessage()));
    }

    @ExceptionHandler(NotFoundClientId.class)
    public ResponseEntity<ErrorMessage> notFoundClientId(NotFoundClientId nt,
                                                         HttpServletRequest request){
        log.error("Api error - ", nt);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, nt.getMessage()));
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> passwordInvalidException(PasswordInvalidException ps,
                                                                 HttpServletRequest request){
        log.error("Api error - ", ps);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ps.getMessage()));
    }

    @ExceptionHandler(BadRequestDeleteUser.class)
    public ResponseEntity<ErrorMessage> badRequestDeleteUser(BadRequestDeleteUser dl, HttpServletRequest request){
        log.error("Api error - ", dl);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, dl.getMessage()));
    }
}
