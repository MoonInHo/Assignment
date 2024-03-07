package com.innovation.assignment.customer.infrastructure.repository;

import com.innovation.assignment.customer.domain.vo.Address;
import com.innovation.assignment.customer.domain.vo.BirthDate;
import com.innovation.assignment.customer.domain.vo.Password;
import com.innovation.assignment.customer.domain.vo.Phone;
import com.innovation.assignment.customer.presentation.dto.request.ChangeCustomerInfoRequestDto;

public interface CustomerCommandRepository {

    void changePassword(Long customerId, Password password);

    void modifyDetails(Long customerId, BirthDate birthDate, Phone phone, Address address);
}
