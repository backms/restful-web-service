package com.example.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// HTTP Status Code
// 2XX -> OK
// 4XX -> Client 오류
// 5XX -> Server 오류

// 500번대 에러를 NOT FOUND 에러로 돌린다 -> 없는 회원을 조회 시 500에러에서 404에러로 변경된다.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
