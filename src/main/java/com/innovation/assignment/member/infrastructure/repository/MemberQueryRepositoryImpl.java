package com.innovation.assignment.member.infrastructure.repository;

import com.innovation.assignment.member.domain.vo.Email;
import com.innovation.assignment.member.domain.vo.Phone;
import com.innovation.assignment.member.infrastructure.dto.GetMembersResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.innovation.assignment.member.domain.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isEmailExist(Email email) {
        return queryFactory
                .selectOne()
                .from(member)
                .where(member.email.eq(email))
                .fetchFirst() != null;
    }

    @Override
    public boolean isPhoneExist(Phone phone) {
        return queryFactory
                .selectOne()
                .from(member)
                .where(member.phone.eq(phone))
                .fetchFirst() != null;
    }

    @Override
    public Page<GetMembersResponseDto> getMembers(Pageable pageable) {
        List<GetMembersResponseDto> fetch = queryFactory
                .select(
                        Projections.fields(
                                GetMembersResponseDto.class,
                                member.id,
                                member.email.email,
                                member.password.password,
                                member.birthDate.birthDate,
                                member.phone.phone,
                                member.address.address,
                                member.address.addressDetail
                        )
                )
                .from(member)
                .orderBy(member.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = Optional.ofNullable(
                queryFactory
                        .select(member.count())
                        .from(member)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(fetch, pageable, totalCount);
    }

    @Override
    public GetMembersResponseDto getMemberByEmail(Email email) {
        return queryFactory
                .select(
                        Projections.fields(
                                GetMembersResponseDto.class,
                                member.id,
                                member.email.email,
                                member.password.password,
                                member.birthDate.birthDate,
                                member.phone.phone,
                                member.address.address,
                                member.address.addressDetail
                        )
                )
                .from(member)
                .where(member.email.eq(email))
                .fetchOne();
    }

    @Override
    public GetMembersResponseDto getMemberByPhone(Phone phone) {
        return queryFactory
                .select(
                        Projections.fields(
                                GetMembersResponseDto.class,
                                member.id,
                                member.email.email,
                                member.password.password,
                                member.birthDate.birthDate,
                                member.phone.phone,
                                member.address.address,
                                member.address.addressDetail
                        )
                )
                .from(member)
                .where(member.phone.eq(phone))
                .fetchOne();
    }
}
