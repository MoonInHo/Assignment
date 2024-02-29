package com.innovation.assignment.customer.domain.entity;

import com.innovation.assignment.customer.domain.vo.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

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

    private Customer(
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

    public static Customer createCustomer(
            Email email,
            Password password,
            BirthDate birthDate,
            Phone phone,
            Address address
    ) {
        return new Customer(email, password, birthDate, phone, address);
    }

    public void passwordEncrypt(PasswordEncoder passwordEncoder) {
        this.password = password.encodedPassword(passwordEncoder);
    }

    public void modifyCustomerDetails(BirthDate birthDate, Phone phone, Address address) {
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
    }
}