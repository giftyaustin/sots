package com.memesots.MemesOTS.lib;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.memesots.MemesOTS.lib.enums.RC;

import lombok.Data;


@Data
public class AppResponse<T> {
    private boolean status = true;
    private int status_code = 200;
    private String message = "Success";
    private int response_code;
    private T data;
    private Object error;


    public AppResponse(T data) {
        this.data = data;
    }

    public AppResponse(boolean status, int status_code, String message, T data, int response_code) {
        this.status = status;
        this.status_code = status_code;
        this.message = message;
        this.data = data;
    }
    public AppResponse(boolean status, int status_code, String message, T data) {
        this.status = status;
        this.status_code = status_code;
        this.message = message;
        this.data = data;
    }
    // For exceptions
    public AppResponse(int status_code, String message, String error, int responseCode) {
        this.status = false;
        this.status_code = status_code;
        this.message = message;
        this.data = null;
    }

    public ResponseEntity<Map<String, Object>> toResponseEntity() {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("status_code", status_code);
        responseMap.put("message", message);
        responseMap.put("data", data);
        responseMap.put("response_code", response_code);

        return new ResponseEntity<>(responseMap, null, 200);
    }
    public ResponseEntity<Map<String, Object>> toResponseEntity(int statusCode, int responseCode) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("status_code", status_code);
        responseMap.put("message", message);
        responseMap.put("data", data);
        responseMap.put("response_code", responseCode);

        return new ResponseEntity<>(responseMap, null, statusCode);
    }

    public static <T> AppResponse<T> success(T data) {
        return new AppResponse<>(true, 200, "Success", data, RC.SUCCESS);
    }
    public static <T> AppResponse<T> success(String message) {
        return new AppResponse<>(true, 200, message, null);
    }

    public static <T> AppResponse<T> exception(String message, int statusCode, int responseCode) {
        return new AppResponse<>(true, 200, message, null);
    }
    public static <T> AppResponse<T> exception(int statusCode, String message,String error, int responseCode) {
        return new AppResponse<>(true, 200, message, null);
    }
    public static <T> AppResponse<T> error(int statusCode, String message, int responseCode) {
        return new AppResponse<>(false, statusCode, message, null);
    }

}
