package com.innovation.assignment.customer.infrastructure.repository;

import com.innovation.assignment.customer.domain.vo.Password;
import com.innovation.assignment.customer.presentation.dto.request.ChangeCustomerInfoRequestDto;

public interface CustomerCommandRepository {

    void changePassword(Long customerId, Password password);
}
