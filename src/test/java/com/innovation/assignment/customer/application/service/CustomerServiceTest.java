package com.innovation.assignment.customer.application.service;

import com.innovation.assignment.customer.domain.entity.Customer;
import com.innovation.assignment.customer.domain.repository.CustomerHistoryRepository;
import com.innovation.assignment.customer.domain.vo.Password;
import com.innovation.assignment.customer.presentation.dto.request.*;
import com.innovation.assignment.exception.exception.customer.*;
import com.innovation.assignment.customer.application.dto.request.CreateCustomerRequestDto;
import com.innovation.assignment.customer.domain.repository.CustomerRepository;
import com.innovation.assignment.customer.infrastructure.dto.response.GetCustomerResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
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

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private CustomerHistoryRepository customerHistoryRepository;

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

    @Test
    @DisplayName("비밀번호 변경 - 변경하려는 비밀번호가 기존 비밀번호와 동일할 경우 예외 발생")
    void samePassword_changePassword_throwException() {
        //given
        Customer customer = createCustomerRequestDto.toEntity();
        ChangePasswordRequestDto changePasswordRequestDto = new ChangePasswordRequestDto(
                1L,
                "testPassword123!",
                "testPassword123!"
        );

        given(customerRepository.getCustomer(1L)).willReturn(Optional.of(customer));
        given(passwordEncoder.matches(any(), any())).willReturn(true);

        //when
        Throwable throwable = catchThrowable(() -> customerService.changePassword(changePasswordRequestDto));

        //then
        assertThat(throwable).isInstanceOf(SamePasswordException.class);
        assertThat(throwable).hasMessage("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
    }

    @Test
    @DisplayName("비밀번호 변경 - 올바른 비밀번호로 비밀번호 변경 시도시 비밀번호 업데이트")
    void properNewPassword_changePassword_updatePassword() {
        //given
        Customer customer = createCustomerRequestDto.toEntity();
        ChangePasswordRequestDto changePasswordRequestDto = new ChangePasswordRequestDto(
                1L,
                "newPassword123!",
                "newPassword123!"
        );

        given(customerRepository.getCustomer(1L)).willReturn(Optional.of(customer));
        given(passwordEncoder.encode(changePasswordRequestDto.newPassword())).willReturn("encodedNewPassword123!");
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        //when
        customerService.changePassword(changePasswordRequestDto);

        //then
        assertThat(customer.password()).isEqualTo("encodedNewPassword123!");
    }

    // 회원 정보 변경 -> @DynamicUpdate 를 이용해서 동적 업데이트를 수행하여 mocking 테스트로는 한계가 있음

    @Test
    @DisplayName("고객 정보 삭제 - 존재하는 고객의 id로 고객 정보 삭제 시도시 고객정보 삭제")
    void existCustomerId_deleteCustomerInfo_deleteCustomerInfoAnd() { //TODO 비동기 이벤트 테스트 고민
        //given
        Customer customer = createCustomerRequestDto.toEntity();
        DeleteCustomerRequestDto deleteCustomerRequestDto =
                new DeleteCustomerRequestDto(1L, "테스트 탈퇴 사유");

        given(customerRepository.getCustomer(any())).willReturn(Optional.ofNullable(customer));

        //when
        customerService.deleteCustomer(deleteCustomerRequestDto);

        //then
        verify(customerRepository, times(1)).delete(any());
    }
}