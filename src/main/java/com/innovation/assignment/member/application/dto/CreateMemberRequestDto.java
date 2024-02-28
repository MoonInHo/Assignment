package com.innovation.assignment.member.application.dto;

import com.innovation.assignment.member.domain.entity.Member;
import com.innovation.assignment.member.domain.vo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberRequestDto {

    private String email;
    private String password;
    private String birthDate;
    private String phone;
    private String address;
    private String addressDetail;

    public Member toEntity() {
        return Member.createMember(
                Email.of(email),
                Password.of(password),
                BirthDate.of(birthDate),
                Phone.of(phone),
                Address.of(address, addressDetail)
        );
    }
}
