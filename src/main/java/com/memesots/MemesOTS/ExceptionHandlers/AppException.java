package com.memesots.MemesOTS.ExceptionHandlers;

import com.memesots.MemesOTS.lib.enums.RC;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class AppException extends Exception {

    private boolean responseStatus;
    private int statusCode;
    private String message;
    private int responseCode = 500;


    public AppException(boolean responseStatus, int statusCode, String message) {
        this.responseStatus = responseStatus;
        this.statusCode = statusCode;
        this.message = message;
    }

    @Data
    public static class UserNotFoundException extends AppException {
        private boolean responseStatus = false;
        private int statusCode = 404;
        private String message = "User Not Found";
        private int responseCode = RC.USER_NOT_FOUND;
    }
}
