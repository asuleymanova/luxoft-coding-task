package com.self.company.exception.handler;

import com.self.company.exception.ResourceNotFoundException;
import com.self.company.util.GenericResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        logger.error("Constraint Violations", ex);
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + " : " + violation.getMessage());
        }
        GenericResponse apiError = new GenericResponse("Constraint Violations", ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(final RuntimeException ex, WebRequest request) {
        logger.error("Illegal State Exception", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("ILLEGAL STATE EXCEPTION", ex.getMessage());
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler({MultipartException.class})
    public ResponseEntity<Object> handleMultipartException(
            final RuntimeException ex, final WebRequest request) {
        logger.error("Resource Not Found", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("Multipart File Exception", ex.getMessage());
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(
            final RuntimeException ex, final WebRequest request) {
        logger.error("Resource Not Found", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("Resource Not Found", ex.getMessage());
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(
            final RuntimeException ex, final WebRequest request) {
        logger.error("IllegalArgumentException", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("Illegal Argument Exception", ex.getMessage());
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternal(
            final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("Server error", ex.getLocalizedMessage());
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        logger.error("Mismatch Type", ex);
        final GenericResponse apiError = new GenericResponse("Mismatch Type", ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
