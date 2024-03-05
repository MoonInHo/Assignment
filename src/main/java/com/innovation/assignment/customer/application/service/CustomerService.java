package com.innovation.assignment.customer.application.service;

import com.innovation.assignment.customer.application.dto.request.CreateCustomerRequestDto;
import com.innovation.assignment.customer.domain.entity.Customer;
import com.innovation.assignment.customer.domain.repository.CustomerRepository;
import com.innovation.assignment.customer.domain.vo.*;
import com.innovation.assignment.customer.infrastructure.dto.response.GetCustomerResponseDto;
import com.innovation.assignment.customer.presentation.dto.request.ChangeCustomerInfoRequestDto;
import com.innovation.assignment.customer.presentation.dto.request.ChangePasswordRequestDto;
import com.innovation.assignment.exception.exceptions.customer.*;
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
        customer.encryptPassword(passwordEncoder);

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
    public GetCustomerResponseDto getCustomer(Long customerId) {
        return customerRepository.getCustomerInfo(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Transactional
    public void changePassword(
            Long customerId,
            ChangePasswordRequestDto changePasswordRequestDto
    ) {
        validatePasswordConfirmation(changePasswordRequestDto);

        Password newPassword = Password.of(changePasswordRequestDto.newPassword());

        customerRepository.changePassword(
                customerId,
                newPassword.encodedPassword(passwordEncoder)
        );
    }

    @Transactional
    public void changeCustomerInfo(
            Long customerId,
            ChangeCustomerInfoRequestDto changeCustomerInfoRequestDto
    ) {
        Customer customer = customerRepository.getCustomer(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        customer.modifyCustomerDetails(
                BirthDate.of(changeCustomerInfoRequestDto.birthDate()),
                Phone.of(changeCustomerInfoRequestDto.phone()),
                Address.of(changeCustomerInfoRequestDto.address(), changeCustomerInfoRequestDto.addressDetail())
        );
    }

    @Transactional
    public void deleteCustomer(Long customerId) {

        Customer customer = customerRepository.getCustomer(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        customerRepository.delete(customer);
    }

    private void checkDuplicateEmail(CreateCustomerRequestDto createCustomerRequestDto) {
        boolean emailExist = customerRepository.isEmailExist(Email.of(createCustomerRequestDto.getEmail()));
        if (emailExist) {
            throw new DuplicateEmailException();
        }
    }

    private void checkDuplicatePhone(CreateCustomerRequestDto createCustomerRequestDto) {
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
