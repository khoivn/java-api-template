package io.toprate.si.exceptionMappers;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponseMessage handleAllExceptions(final Exception exception) {
        exception.printStackTrace();
        return ApiResponseMessage
            .builder()
            .message(exception.getLocalizedMessage())
            .status(HttpStatus.BAD_REQUEST.value())
            .build();
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiResponseMessage handleNotFoundException(final Exception exception) {
        exception.printStackTrace();
        return ApiResponseMessage
            .builder()
            .message(exception.getLocalizedMessage())
            .status(HttpStatus.NOT_FOUND.value())
            .build();
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ApiResponseMessage handleIllegalException(final HttpClientErrorException exception) {
        exception.printStackTrace();
        return ApiResponseMessage
            .builder()
            .message(exception.getLocalizedMessage())
            .status(exception.getStatusCode().value())
            .build();
    }
}
