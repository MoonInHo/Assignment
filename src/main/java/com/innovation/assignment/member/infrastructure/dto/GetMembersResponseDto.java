package com.innovation.assignment.member.infrastructure.dto;

import lombok.Getter;

@Getter
public class GetMembersResponseDto {

    private Long id;
    private String email;
    private String password;
    private String birthDate;
    private String phone;
    private String address;
    private String addressDetail;
}
