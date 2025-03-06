package com.kozich.productservice.controller.exceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.kozich.productservice.core.exception.ForbiddenException;
import com.kozich.productservice.core.exception.UpdateСonflictException;
import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DefaultAdvice {

    private static final String MESSAGE_400 = "Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз";
    private static final String MESSAGE_500 = "Сервер не смог корректно обработать запрос. Пожалуйста обратитесь к администратору";

    @ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class,
            HttpMessageNotReadableException.class, ValueInstantiationException.class})
    public ResponseEntity<ErrorResponse> validaException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("error",
                MESSAGE_400);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<ErrorResponse> forbiddenException(ForbiddenException e) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({IllegalArgumentException.class, UpdateСonflictException.class})
    public ResponseEntity<ErrorResponse> illegalArgException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("error", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({FeignException.class})
    public ResponseEntity<ErrorResponse> exception(FeignException e) {
        ErrorResponse errorResponse = new ErrorResponse("error", MESSAGE_500);
        int status = e.status();
        HttpStatus responseError = HttpStatus.INTERNAL_SERVER_ERROR;
        ObjectMapper objectMapper = new ObjectMapper();
        if (status == 400) {
            try {
                errorResponse.setMessage(
                        objectMapper.readValue(String.valueOf(e.contentUTF8()),
                                ErrorResponse.class).getMessage()
                );
                responseError = HttpStatus.BAD_REQUEST;
            } catch (JsonProcessingException ignored) {
                log.error("Ошибка десериализации");
            }
        }

        return new ResponseEntity<>(errorResponse, responseError);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("error",
                MESSAGE_500);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
