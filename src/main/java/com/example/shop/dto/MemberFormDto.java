package com.example.shop.dto;

import com.example.shop.constant.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberFormDto {

    private String name;

    private String email;

    private String password;

    private String address;

}
