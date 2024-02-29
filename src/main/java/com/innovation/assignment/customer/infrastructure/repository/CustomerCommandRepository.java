package com.innovation.assignment.customer.infrastructure.repository;

import com.innovation.assignment.customer.domain.vo.Password;

public interface CustomerCommandRepository {

    void changePassword(Long memberId, Password password);
}
