package com.pvxdv.loggerspringaop.error;


import com.pvxdv.loggerspringaop.exception.ResourceAlreadyExistException;
import com.pvxdv.loggerspringaop.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Date;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundExceptionResolve(ResourceNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(new Date(), e.getMessage(), ThreadContext.get("request_id")));
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> resourceAlreadyExistExceptionResolve(ResourceAlreadyExistException e){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(new Date(),e.getMessage(), ThreadContext.get("request_id")));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidExceptionResolve(MethodArgumentNotValidException e){
        String validationErrorMassage = "Validation error. " + e.getBindingResult().getFieldErrors().stream()
                .map(error -> "%s:%s".formatted(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(new Date(), validationErrorMassage, ThreadContext.get("request_id")));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidExceptionResolve(ConstraintViolationException e){
        String validationErrorMassage = "Validation error. " + e.getConstraintViolations().stream()
                .map(violation -> "%s:%s".formatted(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.joining(", "));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(new Date(),validationErrorMassage, ThreadContext.get("request_id")));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidExceptionResolve(HandlerMethodValidationException e){
        //todo create readable response
        String validationErrorMassage = "Validation error. " + e.getAllErrors();
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ErrorMessage(new Date(),validationErrorMassage, ThreadContext.get("request_id")));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> resourceOtherExceptionResolve(Exception e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(new Date(),e.getMessage(), ThreadContext.get("request_id")));
    }
}
