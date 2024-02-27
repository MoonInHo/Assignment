package com.innovation.assignment.member.application.service;

import com.innovation.assignment.exception.exception.DuplicatePhoneException;
import com.innovation.assignment.exception.exception.member.DuplicateEmailException;
import com.innovation.assignment.member.application.dto.CreateMemberRequestDto;
import com.innovation.assignment.member.domain.repository.MemberRepository;
import com.innovation.assignment.member.domain.vo.Email;
import com.innovation.assignment.member.domain.vo.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void signUp(CreateMemberRequestDto createMemberRequestDto) {

        checkDuplicateEmail(createMemberRequestDto);
        checkDuplicatePhone(createMemberRequestDto);

        memberRepository.save(createMemberRequestDto.toEntity());
    }

    @Transactional(readOnly = true)
    protected void checkDuplicateEmail(CreateMemberRequestDto createMemberRequestDto) {
        boolean emailExist = memberRepository.isEmailExist(Email.of(createMemberRequestDto.getEmail()));
        if (emailExist) {
            throw new DuplicateEmailException();
        }
    }

    @Transactional(readOnly = true)
    protected void checkDuplicatePhone(CreateMemberRequestDto createMemberRequestDto) {
        boolean phoneExist = memberRepository.isPhoneExist(Phone.of(createMemberRequestDto.getPhone()));
        if (phoneExist) {
            throw new DuplicatePhoneException();
        }
    }
}
