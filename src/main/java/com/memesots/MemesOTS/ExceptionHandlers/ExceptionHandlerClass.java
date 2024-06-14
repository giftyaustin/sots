package com.memesots.MemesOTS.ExceptionHandlers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerClass {



    // ========== Invalid body data ============
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
        errors.put(error.getField(), error.getDefaultMessage()));
        Map<String, Object> response = new HashMap<>();
        response.put("status", false);
        response.put("status_code", 400);
        response.put("errors", errors);
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(response, null, HttpStatus.BAD_REQUEST_400);
        return responseEntity;
    }
    

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


    //  ======== NO BODY =====
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
     public ResponseEntity<Map<String, Object>> handleNoBodyException(Exception ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", false);
        responseBody.put("status_code", HttpStatus.BAD_REQUEST_400);
        responseBody.put("message", "Body is required");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST_400).body(responseBody);
    }



    // ======== FINAL ========
    @ExceptionHandler(value = Exception.class)
     public ResponseEntity<Map<String, Object>> handleMyCustomException(Exception ex) {
        Map<String, Object> responseBody = new HashMap<>();
        System.out.println(ex.getClass().getName() + "====================");
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR_500).body(responseBody);
    }

}
