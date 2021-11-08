package com.example.restfulwebservice.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value = { "passwd"})  // 지정된 필드를 화면에 출력하지 못하게 해줌
@NoArgsConstructor      // 디폴트 생성자
//@JsonFilter("UserInfo")
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min=2, message = "Name은 2글자 이상 입력해주세요.")
    @ApiModelProperty(notes = "사용자 이름을 입력해주세요")
    private String name;
    @Past
    @ApiModelProperty(notes = "등록일을 입력해주세요")
    private Date joinDate;

    @ApiModelProperty(notes = "패스워드를 입력해주세요")
    private String passwd;
    @ApiModelProperty(notes = "사용자의 주민번호를 입력해주세요")
    private String ssn;

    @OneToMany(mappedBy = "user")
    private List<Post> post;


    public User(int id , String name, Date joinDate, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.passwd = password;
        this.ssn = ssn;
    }
}
