package com.example.restfulwebservice.exception;

import com.example.restfulwebservice.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice // 모든 컨트롤러가 실행될때 해당 어노테이션이 선언된 컨트롤러를 인식함.
public class CustomizedResponseEntityExceptionHandler<ReponseEntity> extends ResponseEntityExceptionHandler {

    // 에러가 발생하면 JSON 데이터로 date, message, details를 출력하도록 함.
    @ExceptionHandler(Exception.class)      // 모든 exception에 대해 해당 메소드를 실행한다.
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription((false)));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //사용자가 존재하지 않았을때의 예외처리 생성
    @ExceptionHandler(UserNotFoundException.class)  // UserNotFoundException 에러가 발생했을 때 이 메소드를 실행한다.
    public final ResponseEntity<Object> handleUserNotExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription((false)));

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    // 유효성 에러 발생 시 해당 메소드를 실행
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                "Validation Failed", ex.getBindingResult().toString());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
