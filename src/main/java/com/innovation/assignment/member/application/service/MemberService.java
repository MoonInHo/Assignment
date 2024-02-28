package com.innovation.assignment.member.application.service;

import com.innovation.assignment.exception.exception.DuplicatePhoneException;
import com.innovation.assignment.exception.exception.member.DuplicateEmailException;
import com.innovation.assignment.exception.exception.member.EmptyMemberListException;
import com.innovation.assignment.exception.exception.member.MemberNotFoundException;
import com.innovation.assignment.member.application.dto.CreateMemberRequestDto;
import com.innovation.assignment.member.domain.repository.MemberRepository;
import com.innovation.assignment.member.domain.vo.Email;
import com.innovation.assignment.member.domain.vo.Phone;
import com.innovation.assignment.member.infrastructure.dto.GetMembersResponseDto;
import com.innovation.assignment.member.presentation.dto.SearchMemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void signUp(CreateMemberRequestDto createMemberRequestDto) {

        checkDuplicateEmail(createMemberRequestDto);
        checkDuplicatePhone(createMemberRequestDto);

        //TODO 비밀번호 암호화

        memberRepository.save(createMemberRequestDto.toEntity());
    }

    @Transactional(readOnly = true)
    public Page<GetMembersResponseDto> getMembers(Pageable pageable) {
        Page<GetMembersResponseDto> memberList = memberRepository.getMembers(pageable);
        if (memberList.isEmpty()) {
            throw new EmptyMemberListException(); //TODO 예외 발생시 클라이언트 바디에 메세지가 표시되지않는 이유 찾기
        }
        return memberList;
    }

    @Transactional(readOnly = true)
    public GetMembersResponseDto searchMemberByEmail(SearchMemberRequestDto searchMemberRequestDto) {
        GetMembersResponseDto member = memberRepository.getMemberByEmail(Email.of(searchMemberRequestDto.email()));
        if (member == null) {
            throw new MemberNotFoundException();
        }
        return member;
    }

    @Transactional(readOnly = true)
    public GetMembersResponseDto searchMemberByPhone(SearchMemberRequestDto searchMemberRequestDto) {
        GetMembersResponseDto member = memberRepository.getMemberByPhone(Phone.of(searchMemberRequestDto.phone()));
        if (member == null) {
            throw new MemberNotFoundException();
        }
        return member;
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
