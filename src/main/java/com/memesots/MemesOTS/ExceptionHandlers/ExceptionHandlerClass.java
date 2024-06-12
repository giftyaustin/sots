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
    

    @ExceptionHandler(value = AppException.class)
     public ResponseEntity<Map<String, Object>> handleAppException(AppException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", ex.isResponseStatus());
        responseBody.put("status_code", ex.getStatusCode());
        responseBody.put("message", ex.getMessage());
        int statusCode = 500;
        if(ex.getStatusCode() != 0){
            statusCode = ex.getStatusCode();
        }
        return ResponseEntity.status(statusCode).body(responseBody);
    }
    @ExceptionHandler(value = Exception.class)
     public ResponseEntity<Map<String, Object>> handleMyCustomException(Exception ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR_500).body(responseBody);
    }

}
