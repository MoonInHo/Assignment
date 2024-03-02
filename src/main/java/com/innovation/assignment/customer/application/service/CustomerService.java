package com.innovation.assignment.customer.application.service;

import com.innovation.assignment.customer.application.dto.request.CreateCustomerRequestDto;
import com.innovation.assignment.customer.application.event.CustomerHasDeletedEvent;
import com.innovation.assignment.customer.domain.entity.Customer;
import com.innovation.assignment.customer.domain.entity.CustomerHistory;
import com.innovation.assignment.customer.domain.repository.CustomerHistoryRepository;
import com.innovation.assignment.customer.domain.repository.CustomerRepository;
import com.innovation.assignment.customer.domain.vo.*;
import com.innovation.assignment.customer.infrastructure.dto.response.GetCustomerResponseDto;
import com.innovation.assignment.customer.presentation.dto.request.*;
import com.innovation.assignment.exception.exception.customer.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final CustomerHistoryRepository customerHistoryRepository;
    private final ApplicationEventPublisher eventPublisher;

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

        validatePasswordConfirmation(changePasswordRequestDto);

        Password newPassword = Password.of(changePasswordRequestDto.newPassword());

        Customer customer = customerRepository.getCustomer(changePasswordRequestDto.customerId())
                .orElseThrow(CustomerNotFoundException::new);

        customer.changePassword(newPassword, passwordEncoder);
    }

    @Transactional
    public void changeCustomerInfo(ChangeCustomerInfoRequestDto changeCustomerInfoRequestDto) {

        Customer customer = customerRepository.getCustomer(changeCustomerInfoRequestDto.customerId())
                .orElseThrow(CustomerNotFoundException::new);

        customer.modifyCustomerDetails(
                BirthDate.of(changeCustomerInfoRequestDto.birthDate()),
                Phone.of(changeCustomerInfoRequestDto.phone()),
                Address.of(changeCustomerInfoRequestDto.address(), changeCustomerInfoRequestDto.addressDetail())
        );
    }

    @Transactional
    public void deleteCustomer(DeleteCustomerRequestDto deleteCustomerRequestDto) {

        Customer customer = customerRepository.getCustomer(deleteCustomerRequestDto.customerId())
                .orElseThrow(CustomerNotFoundException::new);

        customerRepository.delete(customer);

        eventPublisher.publishEvent(new CustomerHasDeletedEvent(deleteCustomerRequestDto)); //TODO 코드에서 예외가 발생할 경우 처리 방안 생각해보기
    }

    @Transactional
    public void addCustomerHistory(DeleteCustomerRequestDto deleteCustomerRequestDto) {

        CustomerHistory customerHistory = CustomerHistory.createCustomerHistory(
                deleteCustomerRequestDto.customerId(),
                WithdrawalReason.of(deleteCustomerRequestDto.withdrawalReason())
        );
        customerHistoryRepository.save(customerHistory);
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
