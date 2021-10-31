package com.example.restfulwebservice.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkRelationProvider;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private UserDaoService service;

    public UserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retriveAllUser(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retriveUser(@PathVariable int id){
        User user = service.findOne(id);

        // DB에 없는 사용자를 조회 시, 서버의 프로그램상에는 문제가 없으므로 200 ok를 반환한다.
        // 반면 화면상에는 user not found로 공백 화면이 출력된다.
        // -> 존재하지 않는 이용자 조회시 user not found 500에러를 출력함
        if(user == null){
            throw new UserNotFoundException(String.format("Id[%s] not Found", id));
        }

        // HATEOAS
        EntityModel model = EntityModel.of(user);

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retriveAllUser());  // retriveAllUser와 "all-users"를 연결
        // postman의 결과에 _links - all-users - href 에 all-users의 풀 uri가 출력
        // hateoas 를 사용하면 하나의 작업에 대해 파생된 추가적인 작업들을 실행할 수 있음.
        model.add(linkTo.withRel("all-users"));

        return model;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User saveUser = service.save(user);

        // 서버에서 결과에 따른 상태값(200) 이 아닌 상태코드 created가 출력되어야 올바른 REST API를 설계하는 방법임)
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // 사용자로 부터 받은 요청 url -> '/users'
                .path("/{id}")                                          // 가변적인 값
                .buildAndExpand(saveUser.getId())                       // user 객체에 생성된 id 값
                .toUri();                                               // uri 형태로 적용

        // 이렇게 리턴을 하면 요청 결과가 201 Created로 변경되고,
        // 결과 - header - location 에 반환된 url 값이 출력되어있다.
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);

        if(user == null) {
            throw new UserNotFoundException(String.format("Id[%s] Not Found", id));
        }
    }

}
