package com.innovation.assignment.member.infrastructure.repository;

import com.innovation.assignment.member.domain.vo.Email;
import com.innovation.assignment.member.domain.vo.Phone;

public interface MemberQueryRepository {

    boolean isEmailExist(Email email);

    boolean isPhoneExist(Phone phone);
}
