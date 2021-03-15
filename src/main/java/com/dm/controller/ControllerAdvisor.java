package com.dm.controller;

import com.dm.WarehouseManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ControllerAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception e) {
        logger.error(e.getMessage(), e.getCause());

        if (e instanceof WarehouseManagerException) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if (e instanceof MethodArgumentTypeMismatchException) {
            return new ResponseEntity<>("Argument type mismatch for: " +
                    ((MethodArgumentTypeMismatchException) e).getName(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
