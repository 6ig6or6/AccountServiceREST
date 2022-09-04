package com.account.accountservice.dto.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponseDTO {

    private final String name;
    private final String lastname;
    private final String period;
    private final String salary;
}
