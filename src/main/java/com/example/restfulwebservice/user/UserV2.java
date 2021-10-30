package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value = { "passwd"})  // 지정된 필드를 화면에 출력하지 못하게 해줌
@NoArgsConstructor
@JsonFilter("UserInfoV2")
public class UserV2 extends User {

    private String grade;

}
