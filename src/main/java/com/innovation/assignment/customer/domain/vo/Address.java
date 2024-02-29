package com.innovation.assignment.customer.domain.vo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Address {

    private final String address;
    private final String addressDetail;

    private Address(String address, String addressDetail) {
        this.address = address;
        this.addressDetail = addressDetail;
    }

    public static Address of(String address, String addressDetail) {
        return new Address(address, addressDetail);
    }
}
