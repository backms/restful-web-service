package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private UserDaoService service;

    public AdminUserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retriveAllUser(){

        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "passwd");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue retriveUser(@PathVariable int id){

        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("Id[%s] not Found", id));
        }

        // User객체의 프로퍼티를 적용받을 수 있도록 필터 설정
        // 필터 타입을 설정 - 필터를 통해 출력할 필드들을 설정한다.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "passwd", "ssn");

        // 필터를 사용할 수 있게 user 객체의 선언된 어노테이션("userInfo")와 filter를 매칭해주어 설정함
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // 클라이언트한테 반환할 오브젝트 타입을 객체가 아닌 필터가 적용될 수 있도록 매핑 밸류 타입으로 적용
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

}
