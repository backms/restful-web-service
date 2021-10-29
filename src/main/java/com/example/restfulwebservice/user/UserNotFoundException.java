package com.example.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// HTTP Status Code
// 2XX -> OK
// 4XX -> Client 오류
// 5XX -> Server 오류

// 존재하지 않는 회원 조회 시 예외처리를 발생시키기 때문에 500에러를 발생시킴 -> 특이사항에 원치않는 정보를 노출할 수 있음
// 존재하지 않는 회원 조회 시 500번대 에러가 발생하지 않고 400번대 에러가 출력되도록 변경된다.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
