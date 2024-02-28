package com.innovation.assignment.member.infrastructure.repository;

import com.innovation.assignment.member.domain.vo.Email;
import com.innovation.assignment.member.domain.vo.Phone;
import com.innovation.assignment.member.infrastructure.dto.GetMembersResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberQueryRepository {

    boolean isEmailExist(Email email);

    boolean isPhoneExist(Phone phone);

    Page<GetMembersResponseDto> getMembers(Pageable pageable);

    GetMembersResponseDto getMemberByEmail(Email email);

    GetMembersResponseDto getMemberByPhone(Phone phone);
}
