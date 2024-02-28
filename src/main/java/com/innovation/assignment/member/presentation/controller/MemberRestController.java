package com.innovation.assignment.member.presentation.controller;

import com.innovation.assignment.member.application.dto.CreateMemberRequestDto;
import com.innovation.assignment.member.application.service.MemberService;
import com.innovation.assignment.member.infrastructure.dto.GetMembersResponseDto;
import com.innovation.assignment.member.presentation.dto.SearchMemberByEmailRequestDto;
import com.innovation.assignment.member.presentation.dto.SearchMemberByPhoneRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(
            @RequestBody CreateMemberRequestDto createMemberRequestDto
    ) {
        memberService.signUp(createMemberRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<GetMembersResponseDto>> getMembers(Pageable pageable) {
        return ResponseEntity.ok().body(memberService.getMembers(pageable));
    }

    @PostMapping("/search/email")
    public ResponseEntity<GetMembersResponseDto> searchMemberByEmail(
            @RequestBody SearchMemberByEmailRequestDto searchMemberByEmailRequestDto
    ) {
        return ResponseEntity.ok().body(memberService.searchMemberByEmail(searchMemberByEmailRequestDto));
    }

    @PostMapping("/search/phone")
    public ResponseEntity<GetMembersResponseDto> searchMemberByPhone(
            @RequestBody SearchMemberByPhoneRequestDto searchMemberByPhoneRequestDto
    ) {
        return ResponseEntity.ok().body(memberService.searchMemberByPhone(searchMemberByPhoneRequestDto));
    }
}
