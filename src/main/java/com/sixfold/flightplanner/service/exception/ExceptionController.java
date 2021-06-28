package com.sixfold.flightplanner.service.exception;

import com.sixfold.flightplanner.service.exception.types.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = ResourceNotFound.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFound e, WebRequest request) {
        final HttpStatus status = HttpStatus.NOT_FOUND;
        return getErrorMessageResponseEntity(e, request, status);
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> handleAnyException(Exception e, WebRequest request) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return getErrorMessageResponseEntity(e, request, status);
    }


    private ResponseEntity<ErrorMessage> getErrorMessageResponseEntity(Exception e, WebRequest request, HttpStatus status) {
        final String timeNow = ZonedDateTime.now(ZoneId.of("Europe/Tallinn")).toString();
        ErrorMessage errorMessage = new ErrorMessage(
                e.getMessage(),
                e,
                status,
                timeNow,
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, status);
    }

}