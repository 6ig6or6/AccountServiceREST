package com.account.accountservice.controller;

import com.account.accountservice.dto.payment.PaymentDTO;
import com.account.accountservice.dto.user.StatusResponseDTO;
import com.account.accountservice.mapper.ModelMapper;
import com.account.accountservice.model.payment.Payment;
import com.account.accountservice.service.PaymentService;
import com.account.accountservice.validation.ValidDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import java.util.List;


@Validated
@RestController
public class AccountController {

    private final PaymentService paymentService;
    private final ModelMapper modelMapper;

    @Autowired
    public AccountController(PaymentService paymentService, ModelMapper modelMapper) {
        this.paymentService = paymentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/api/acct/payments")
    public StatusResponseDTO uploadPayrolls(@RequestBody
                                            @NotEmpty(message = "Input salary list can't be empty.")
                                            List<@Valid PaymentDTO> payrollDTO) {
        paymentService.uploadPayrolls(payrollDTO);
        return new StatusResponseDTO("Added successfully!");
    }

    @PutMapping("/api/acct/payments")
    public StatusResponseDTO updatePayment(@Valid @RequestBody PaymentDTO dto) {
        Payment payment = modelMapper.mapToEntity(dto);
        paymentService.updatePayment(payment);

        return new StatusResponseDTO("Updated successfully!");
    }

    @GetMapping("/api/empl/payment")
    public ResponseEntity<?> getPayments(@ValidDate @RequestParam(required = false) String period,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        Object reply = paymentService.getPaymentsByPeriod(period, userDetails);
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }
}
