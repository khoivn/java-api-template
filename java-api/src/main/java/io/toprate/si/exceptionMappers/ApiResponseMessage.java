package io.toprate.si.exceptionMappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
@Getter
@With
@AllArgsConstructor
public class ApiResponseMessage {

    private String message;
    private int status;
    private Object data;
    private static final String SUCCESS = "success";
    private static final String FAILED = "failed";
    private static final String NOT_FOUND = "not found";

    public static ResponseEntity<ApiResponseMessage> success() {
        return ResponseEntity
            .ok()
            .body(ApiResponseMessage.builder().message(SUCCESS).status(HttpStatus.OK.value()).build());
    }

    public static ResponseEntity<ApiResponseMessage> success(Object data) {
        System.out.println(SUCCESS);
        return ResponseEntity
            .ok()
            .body(ApiResponseMessage.builder().message(SUCCESS).status(HttpStatus.OK.value()).data(data).build());
    }

    public static ResponseEntity<ApiResponseMessage> success(String message) {
        return ResponseEntity
            .ok()
            .body(ApiResponseMessage.builder().message(message).status(HttpStatus.OK.value()).build());
    }

    public static ResponseEntity<ApiResponseMessage> success(String message, Object data) {
        return ResponseEntity
            .ok()
            .body(ApiResponseMessage.builder().message(message).status(HttpStatus.OK.value()).data(data).build());
    }

    public static ResponseEntity<ApiResponseMessage> createSuccess(Object data) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponseMessage.builder().message(SUCCESS).status(HttpStatus.OK.value()).data(data).build());
    }

    public static ResponseEntity<ApiResponseMessage> badRequest() {
        return ResponseEntity
            .badRequest()
            .body(ApiResponseMessage.builder().message(FAILED).status(HttpStatus.OK.value()).build());
    }

    public static ResponseEntity<Object> badRequestObj(String message) {
        return ResponseEntity
            .badRequest()
            .body(ApiResponseMessage.builder().message(message).status(HttpStatus.OK.value()).build());
    }

    public static ResponseEntity<ApiResponseMessage> notFound() {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponseMessage.builder().message(NOT_FOUND).status(HttpStatus.OK.value()).build());
    }

    public static ResponseEntity<ApiResponseMessage> build(HttpStatus status, String message, Object data) {
        return ResponseEntity
            .status(status)
            .body(ApiResponseMessage.builder().message(SUCCESS).status(HttpStatus.OK.value()).data(data).build());
    }
}