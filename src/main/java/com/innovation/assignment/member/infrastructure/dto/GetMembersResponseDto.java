package com.innovation.assignment.member.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetMembersResponseDto {

    private Long id;
    private String email;
    private String password;
    private String birthDate;
    private String phone;
    private String address;
    private String addressDetail;
}
