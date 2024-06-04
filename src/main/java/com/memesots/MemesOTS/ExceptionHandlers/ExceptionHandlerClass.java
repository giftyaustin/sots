package com.memesots.MemesOTS.ExceptionHandlers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerClass {
    

    @ExceptionHandler(value = Exception.class)
     public ResponseEntity<Map<String, Object>> handleMyCustomException(Exception ex) {
        Map<String, Object> responseBody = new HashMap();
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR_500).body(responseBody);
    }

}
