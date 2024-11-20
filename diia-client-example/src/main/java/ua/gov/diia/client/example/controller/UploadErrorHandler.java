package ua.gov.diia.client.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = UploadController.class)
public class UploadErrorHandler {
    private static final String FAILED_RESPONSE_BODY = "{\"success\": false, \"error\": \"%s\"}";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(String.format(FAILED_RESPONSE_BODY, e.getMessage()));
    }
}
