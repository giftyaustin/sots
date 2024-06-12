package com.memesots.MemesOTS.ExceptionHandlers;

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
}
