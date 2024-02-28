package com.innovation.assignment.member.application.service;

import com.innovation.assignment.exception.exception.DuplicatePhoneException;
import com.innovation.assignment.exception.exception.member.DuplicateEmailException;
import com.innovation.assignment.exception.exception.member.EmptyMemberListException;
import com.innovation.assignment.exception.exception.member.MemberNotFoundException;
import com.innovation.assignment.member.application.dto.CreateMemberRequestDto;
import com.innovation.assignment.member.domain.repository.MemberRepository;
import com.innovation.assignment.member.infrastructure.dto.GetMembersResponseDto;
import com.innovation.assignment.member.presentation.dto.SearchMemberByEmailRequestDto;
import com.innovation.assignment.member.presentation.dto.SearchMemberByPhoneRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("[유닛 테스트] - 회원 서비스")
class MemberServiceTest {

    private CreateMemberRequestDto createMemberRequestDto;

    private GetMembersResponseDto getMembersResponseDto;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setMemberInfo() {

        createMemberRequestDto = new CreateMemberRequestDto(
                "test123@gmail.com",
                "testPassword123!",
                "1995.11.11",
                "010-1234-5678",
                "서울특별시 강남구 강남대로",
                "지하396"
        );

        getMembersResponseDto = new GetMembersResponseDto(
                1L,
                "test123@naver.com",
                "testPassword123!",
                "1995.11.11",
                "010.1234.5678",
                "서울특별시 강남구 강남대로",
                "지하396"
        );
    }

    @Test
    @DisplayName("이메일 중복 확인 - 중복된 이메일로 회원가입 시도시 예외 발생")
    void duplicateEmail_signUp_throwException() {
        //given
        given(memberRepository.isEmailExist(any())).willReturn(true);

        //when
        Throwable throwable = catchThrowable(() -> memberService.signUp(createMemberRequestDto));

        //then
        assertThat(throwable).isInstanceOf(DuplicateEmailException.class);
        assertThat(throwable).hasMessage("이미 사용중인 이메일 입니다.");
    }

    @Test
    @DisplayName("연락처 중복 확인 - 중복된 연락처로 회원가입 시도시 예외 발생")
    void duplicatePhone_signUp_throwException() {
        given(memberRepository.isPhoneExist(any())).willReturn(true);

        //when
        Throwable throwable = catchThrowable(() -> memberService.signUp(createMemberRequestDto));

        //then
        assertThat(throwable).isInstanceOf(DuplicatePhoneException.class);
        assertThat(throwable).hasMessage("해당 연락처로 가입 정보가 존재합니다.");
    }

    @Test
    @DisplayName("회원 가입 - 올바른 정보로 회원가입 시도시 사용자 정보 저장")
    void properInfo_signUp_saveUserInfo() {
        //given
        given(memberRepository.isEmailExist(any())).willReturn(false);
        given(memberRepository.isPhoneExist(any())).willReturn(false);

        //when
        memberService.signUp(createMemberRequestDto);

        //then
        verify(memberRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("회원 목록 조회 - 비어있는 회원 목록 조회시 예외 발생")
    void RetrieveEmptyMemberList_throwException() {
        //given & when
        List<GetMembersResponseDto> emptyMemberList = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<GetMembersResponseDto> emptyPage = new PageImpl<>(emptyMemberList, pageRequest, 0);

        given(memberRepository.getMembers(pageable)).willReturn(emptyPage);
        Throwable throwable = catchThrowable(() -> memberService.getMembers(pageable));

        //then
        assertThat(throwable).isInstanceOf(EmptyMemberListException.class);
        assertThat(throwable).hasMessage("회원 목록이 비어있습니다.");
    }

    @Test
    @DisplayName("회원 목록 조회 - 비어있지 않은 회원 목록 조회시 회원 목록 반환")
    void notEmptyMemberList_retrieve_returnMemberList() {
        //given
        List<GetMembersResponseDto> memberList = new ArrayList<>();
        memberList.add(getMembersResponseDto);
        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<GetMembersResponseDto> memberPage = new PageImpl<>(memberList, pageRequest, 2);

        given(memberRepository.getMembers(pageable)).willReturn(memberPage);

        //when
        Page<GetMembersResponseDto> members = memberService.getMembers(pageable);

        //then
        assertThat(members).isNotEmpty();
        assertThat(members).hasSize(1);
    }

    @Test
    @DisplayName("이메일로 회원 검색 - 존재하지 않는 회원 이메일로 회원을 검색할 경우 예외 발생")
    void notExistMemberEmail_searchMember_throwException() {
        //given
        SearchMemberByEmailRequestDto searchMemberByEmailRequestDto =
                new SearchMemberByEmailRequestDto("notExistEmail@naver.com");

        given(memberRepository.getMemberByEmail(any())).willReturn(null);

        //when
        Throwable throwable = catchThrowable(() -> memberService.searchMemberByEmail(searchMemberByEmailRequestDto));

        //then
        assertThat(throwable).isInstanceOf(MemberNotFoundException.class);
        assertThat(throwable).hasMessage("회원을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("이메일로 회원 검색 - 존재하는 회원의 이메일로 회원 검색시 회원 정보 반환")
    void existMemberEmail_searchMember_returnMemberInfo() {
        //given
        SearchMemberByEmailRequestDto searchMemberByEmailRequestDto =
                new SearchMemberByEmailRequestDto("existEmail@naver.com");

        given(memberRepository.getMemberByEmail(any())).willReturn(getMembersResponseDto);

        //when
        GetMembersResponseDto memberInfo = memberService.searchMemberByEmail(searchMemberByEmailRequestDto);

        //then
        assertThat(memberInfo).isNotNull();
        assertThat(memberInfo.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("연락처로 회원 검색 - 존재하지 않는 회원 연락처로 회원을 검색할 경우 예외 발생")
    void notExistMemberPhone_searchMember_throwException() {
        SearchMemberByPhoneRequestDto searchMemberByPhoneRequestDto =
                new SearchMemberByPhoneRequestDto("010-1234-5678");

        given(memberRepository.getMemberByPhone(any())).willReturn(null);

        //when
        Throwable throwable = catchThrowable(() -> memberService.searchMemberByPhone(searchMemberByPhoneRequestDto));

        //then
        assertThat(throwable).isInstanceOf(MemberNotFoundException.class);
        assertThat(throwable).hasMessage("회원을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("연락처로 회원 검색 - 존재하는 회원의 연락처로 회원 검색시 회원 정보 반환")
    void existMemberPhone_searchMember_returnMemberInfo() {
        //given
        SearchMemberByPhoneRequestDto searchMemberByPhoneRequestDto =
                new SearchMemberByPhoneRequestDto("010-1234-5678");

        given(memberRepository.getMemberByPhone(any())).willReturn(getMembersResponseDto);

        //when
        GetMembersResponseDto memberInfo = memberService.searchMemberByPhone(searchMemberByPhoneRequestDto);

        //then
        assertThat(memberInfo).isNotNull();
        assertThat(memberInfo.getId()).isEqualTo(1L);
    }
}