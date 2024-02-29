package com.innovation.assignment.customer.application.service;

import com.innovation.assignment.customer.application.dto.CreateCustomerRequestDto;
import com.innovation.assignment.customer.domain.entity.Customer;
import com.innovation.assignment.customer.domain.repository.CustomerRepository;
import com.innovation.assignment.customer.domain.vo.*;
import com.innovation.assignment.customer.infrastructure.dto.GetCustomerResponseDto;
import com.innovation.assignment.customer.presentation.dto.ChangeCustomerInfoRequestDto;
import com.innovation.assignment.customer.presentation.dto.ChangePasswordRequestDto;
import com.innovation.assignment.customer.presentation.dto.SearchCustomerByEmailRequestDto;
import com.innovation.assignment.customer.presentation.dto.SearchCustomerByPhoneRequestDto;
import com.innovation.assignment.exception.exception.DuplicatePhoneException;
import com.innovation.assignment.exception.exception.customer.CustomerNotFoundException;
import com.innovation.assignment.exception.exception.customer.DuplicateEmailException;
import com.innovation.assignment.exception.exception.customer.EmptyCustomerListException;
import com.innovation.assignment.exception.exception.customer.PasswordConfirmationMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Transactional
    public void signUp(CreateCustomerRequestDto createCustomerRequestDto) {

        checkDuplicateEmail(createCustomerRequestDto);
        checkDuplicatePhone(createCustomerRequestDto);

        Customer customer = createCustomerRequestDto.toEntity();
        customer.passwordEncrypt(passwordEncoder);
        customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public Page<GetCustomerResponseDto> getCustomers(Pageable pageable) {
        Page<GetCustomerResponseDto> customers = customerRepository.getCustomers(pageable);
        if (customers.isEmpty()) {
            throw new EmptyCustomerListException(); //TODO 예외 발생시 http body 에 예외 메세지가 표시되지않는 이유 찾기
        }
        return customers;
    }

    @Transactional(readOnly = true)
    public GetCustomerResponseDto searchCustomerByEmail(
            SearchCustomerByEmailRequestDto searchCustomerByEmailRequestDto
    ) {
        return customerRepository.getCustomerByEmail(Email.of(searchCustomerByEmailRequestDto.email()))
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public GetCustomerResponseDto searchCustomerByPhone(
            SearchCustomerByPhoneRequestDto searchCustomerByPhoneRequestDto
    ) {
        return customerRepository.getCustomerByPhone(Phone.of(searchCustomerByPhoneRequestDto.phone()))
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Transactional
    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto) {

        Long customerId = changePasswordRequestDto.customerId();
        validatePasswordConfirmation(changePasswordRequestDto);

        Password password = Password.of(changePasswordRequestDto.newPassword());
        Password encodedNewPassword = password.encodedPassword(passwordEncoder);

        customerRepository.changePassword(customerId, encodedNewPassword);
    }

    @Transactional
    public void changeCustomerInfo(ChangeCustomerInfoRequestDto changeCustomerInfoRequestDto) {

        Customer customer = customerRepository.getCustomer(changeCustomerInfoRequestDto.customerId())
                .orElseThrow(CustomerNotFoundException::new);

        customer.modifyCustomerDetails(
                BirthDate.of(changeCustomerInfoRequestDto.birthDate()),
                Phone.of(changeCustomerInfoRequestDto.phone()),
                Address.of(
                        changeCustomerInfoRequestDto.address(),
                        changeCustomerInfoRequestDto.addressDetail()
                )
        );
    }

    @Transactional(readOnly = true)
    protected void checkDuplicateEmail(CreateCustomerRequestDto createCustomerRequestDto) {
        boolean emailExist = customerRepository.isEmailExist(Email.of(createCustomerRequestDto.getEmail()));
        if (emailExist) {
            throw new DuplicateEmailException();
        }
    }

    @Transactional(readOnly = true)
    protected void checkDuplicatePhone(CreateCustomerRequestDto createCustomerRequestDto) {
        boolean phoneExist = customerRepository.isPhoneExist(Phone.of(createCustomerRequestDto.getPhone()));
        if (phoneExist) {
            throw new DuplicatePhoneException();
        }
    }

    private void validatePasswordConfirmation(ChangePasswordRequestDto changePasswordRequestDto) {
        String newPassword = changePasswordRequestDto.newPassword();
        String confirmNewPassword = changePasswordRequestDto.confirmNewPassword();
        if (!newPassword.equals(confirmNewPassword)) {
            throw new PasswordConfirmationMismatchException();
        }
    }
}
