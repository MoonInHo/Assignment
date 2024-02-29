package com.innovation.assignment.customer.application.service;

import com.innovation.assignment.exception.exception.DuplicatePhoneException;
import com.innovation.assignment.exception.exception.customer.DuplicateEmailException;
import com.innovation.assignment.exception.exception.customer.EmptyCustomerListException;
import com.innovation.assignment.exception.exception.customer.CustomerNotFoundException;
import com.innovation.assignment.customer.application.dto.CreateCustomerRequestDto;
import com.innovation.assignment.customer.domain.repository.CustomerRepository;
import com.innovation.assignment.customer.infrastructure.dto.GetCustomerResponseDto;
import com.innovation.assignment.customer.presentation.dto.SearchCustomerByEmailRequestDto;
import com.innovation.assignment.customer.presentation.dto.SearchCustomerByPhoneRequestDto;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("[유닛 테스트] - 회원 서비스")
class CustomerServiceTest {

    private CreateCustomerRequestDto createCustomerRequestDto;

    private GetCustomerResponseDto getCustomerResponseDto;

    @Mock
    private Pageable pageable;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setCustomerInfo() {

        createCustomerRequestDto = new CreateCustomerRequestDto(
                "test123@gmail.com",
                "testPassword123!",
                "1995.11.11",
                "010-1234-5678",
                "서울특별시 강남구 강남대로",
                "지하396"
        );

        getCustomerResponseDto = new GetCustomerResponseDto(
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
        given(customerRepository.isEmailExist(any())).willReturn(true);

        //when
        Throwable throwable = catchThrowable(() -> customerService.signUp(createCustomerRequestDto));

        //then
        assertThat(throwable).isInstanceOf(DuplicateEmailException.class);
        assertThat(throwable).hasMessage("이미 사용중인 이메일 입니다.");
    }

    @Test
    @DisplayName("연락처 중복 확인 - 중복된 연락처로 회원가입 시도시 예외 발생")
    void duplicatePhone_signUp_throwException() {
        given(customerRepository.isPhoneExist(any())).willReturn(true);

        //when
        Throwable throwable = catchThrowable(() -> customerService.signUp(createCustomerRequestDto));

        //then
        assertThat(throwable).isInstanceOf(DuplicatePhoneException.class);
        assertThat(throwable).hasMessage("해당 연락처로 가입 정보가 존재합니다.");
    }

    @Test
    @DisplayName("회원 가입 - 올바른 정보로 회원가입 시도시 사용자 정보 저장")
    void properInfo_signUp_saveUserInfo() {
        //given
        given(customerRepository.isEmailExist(any())).willReturn(false);
        given(customerRepository.isPhoneExist(any())).willReturn(false);

        //when
        customerService.signUp(createCustomerRequestDto);

        //then
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("회원 목록 조회 - 비어있는 회원 목록 조회시 예외 발생")
    void RetrieveEmptyCustomerList_throwException() {
        //given & when
        List<GetCustomerResponseDto> emptyCustomerList = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<GetCustomerResponseDto> emptyPage = new PageImpl<>(emptyCustomerList, pageRequest, 0);

        given(customerRepository.getCustomers(pageable)).willReturn(emptyPage);
        Throwable throwable = catchThrowable(() -> customerService.getCustomers(pageable));

        //then
        assertThat(throwable).isInstanceOf(EmptyCustomerListException.class);
        assertThat(throwable).hasMessage("고객 목록이 비어있습니다.");
    }

    @Test
    @DisplayName("회원 목록 조회 - 비어있지 않은 회원 목록 조회시 회원 목록 반환")
    void notEmptyCustomerList_retrieve_returnCustomerList() {
        //given
        List<GetCustomerResponseDto> customerList = new ArrayList<>();
        customerList.add(getCustomerResponseDto);
        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<GetCustomerResponseDto> customerPage = new PageImpl<>(customerList, pageRequest, 2);

        given(customerRepository.getCustomers(pageable)).willReturn(customerPage);

        //when
        Page<GetCustomerResponseDto> customers = customerService.getCustomers(pageable);

        //then
        assertThat(customers).isNotEmpty();
        assertThat(customers).hasSize(1);
    }

    @Test
    @DisplayName("이메일로 회원 검색 - 존재하지 않는 회원 이메일로 회원을 검색할 경우 예외 발생")
    void notExistCustomerEmail_searchCustomer_throwException() {
        //given
        SearchCustomerByEmailRequestDto searchCustomerByEmailRequestDto =
                new SearchCustomerByEmailRequestDto("notExistEmail@naver.com");

        given(customerRepository.getCustomerByEmail(any())).willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> customerService.searchCustomerByEmail(searchCustomerByEmailRequestDto));

        //then
        assertThat(throwable).isInstanceOf(CustomerNotFoundException.class);
        assertThat(throwable).hasMessage("고객을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("이메일로 회원 검색 - 존재하는 회원의 이메일로 회원 검색시 회원 정보 반환")
    void existCustomerEmail_searchCustomer_returnCustomerInfo() {
        //given
        SearchCustomerByEmailRequestDto searchCustomerByEmailRequestDto =
                new SearchCustomerByEmailRequestDto("existEmail@naver.com");

        given(customerRepository.getCustomerByEmail(any())).willReturn(Optional.ofNullable(getCustomerResponseDto));

        //when
        GetCustomerResponseDto customerInfo = customerService.searchCustomerByEmail(searchCustomerByEmailRequestDto);

        //then
        assertThat(customerInfo).isNotNull();
        assertThat(customerInfo.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("연락처로 회원 검색 - 존재하지 않는 회원 연락처로 회원을 검색할 경우 예외 발생")
    void notExistCustomerPhone_searchCustomer_throwException() {
        SearchCustomerByPhoneRequestDto searchCustomerByPhoneRequestDto =
                new SearchCustomerByPhoneRequestDto("010-1234-5678");

        given(customerRepository.getCustomerByPhone(any())).willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> customerService.searchCustomerByPhone(searchCustomerByPhoneRequestDto));

        //then
        assertThat(throwable).isInstanceOf(CustomerNotFoundException.class);
        assertThat(throwable).hasMessage("고객을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("연락처로 회원 검색 - 존재하는 회원의 연락처로 회원 검색시 회원 정보 반환")
    void existCustomerPhone_searchCustomer_returnCustomerInfo() {
        //given
        SearchCustomerByPhoneRequestDto searchCustomerByPhoneRequestDto =
                new SearchCustomerByPhoneRequestDto("010-1234-5678");

        given(customerRepository.getCustomerByPhone(any())).willReturn(Optional.ofNullable(getCustomerResponseDto));

        //when
        GetCustomerResponseDto customerInfo = customerService.searchCustomerByPhone(searchCustomerByPhoneRequestDto);

        //then
        assertThat(customerInfo).isNotNull();
        assertThat(customerInfo.getId()).isEqualTo(1L);
    }
}