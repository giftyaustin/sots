package com.memesots.MemesOTS.ExceptionHandlers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.memesots.MemesOTS.ExceptionHandlers.AppException.UserNotFoundException;
import com.memesots.MemesOTS.lib.AppResponse;
import com.memesots.MemesOTS.lib.enums.RC;

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



    // ========= APP EXCEPTION =========
    @ExceptionHandler(value = AppException.class)
     public ResponseEntity<Map<String, Object>> handleAppException(AppException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", ex.isResponseStatus());
        responseBody.put("status_code", ex.getStatusCode());
        responseBody.put("message", ex.getMessage());
        responseBody.put("response_code", ex.getResponseCode());
        int statusCode = 500;
        if(ex.getStatusCode() != 0){
            statusCode = ex.getStatusCode();
        }
        return ResponseEntity.status(statusCode).body(responseBody);
    }



    // ======== DUPLICATE DATABASE ENTRY =====
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateEntryException(DataIntegrityViolationException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", false);
        responseBody.put("status_code", HttpStatus.CONFLICT_409);
        responseBody.put("message", "Duplicate entry");
        responseBody.put("response_code", RC.DUPLICATE_ENTRY);
        return ResponseEntity.status(HttpStatus.CONFLICT_409).body(responseBody);
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

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        return AppResponse.error(ex.getStatusCode(), ex.getMessage(), ex.getResponseCode()).toResponseEntity(404, ex.getResponseCode());
    }


    // ======== FINAL ========
    @ExceptionHandler(value = Exception.class)
     public ResponseEntity<Map<String, Object>> handleMyCustomException(Exception ex) {
        Map<String, Object> responseBody = new HashMap<>();
        System.out.println(ex.getClass().getName() + "====================");
        responseBody.put("message", ex.getMessage());
        responseBody.put("status", false);
        responseBody.put("status_code", HttpStatus.INTERNAL_SERVER_ERROR_500);
        responseBody.put("response_code", RC.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR_500).body(responseBody);
    }

}
