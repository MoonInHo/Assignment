package com.innovation.assignment.member.domain.entity;

import com.innovation.assignment.member.domain.vo.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false, unique = true)
    private Email email;

    @Embedded
    @Column(nullable = false)
    private Password password;

    @Embedded
    private BirthDate birthDate;

    @Embedded
    @Column(nullable = false, unique = true)
    private Phone phone;

    @Embedded
    private Address address;

    private Member(
            Email email,
            Password password,
            BirthDate birthDate,
            Phone phone,
            Address address
    ) {
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
    }

    public static Member createMember(
            Email email,
            Password password,
            BirthDate birthDate,
            Phone phone,
            Address address
    ) {
        return new Member(email, password, birthDate, phone, address);
    }
}