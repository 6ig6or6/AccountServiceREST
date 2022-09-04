package com.account.accountservice.service;

import com.account.accountservice.dto.payment.PaymentDTO;
import com.account.accountservice.exception.PaymentNotFoundException;
import com.account.accountservice.mapper.ModelMapper;
import com.account.accountservice.model.payment.Payment;
import com.account.accountservice.model.user.User;
import com.account.accountservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.transaction.Transactional;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ModelMapper modelMapper, UserService userService) {
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Transactional
    public void savePayments(List<Payment> payroll) {
        payroll.forEach(p -> {
            if (paymentRepository.findByEmailAndPeriod(p.getEmail(), p.getPeriod()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        });
        paymentRepository.saveAll(payroll);
    }

    @Transactional
    public void updatePayment(Payment paymentUpdate) {
        Payment payment = paymentRepository
                .findByEmailAndPeriod(paymentUpdate.getEmail(), paymentUpdate.getPeriod())
                .orElseThrow(PaymentNotFoundException::new);
        payment.setSalary(paymentUpdate.getSalary());
        paymentRepository.save(payment);
    }

    public Payment getPayment(String email, String period) {
        return paymentRepository.findByEmailAndPeriod(email, YearMonth.parse(period,
                        DateTimeFormatter.ofPattern("MM-yyyy")).atDay(1))
                .orElseThrow(PaymentNotFoundException::new);
    }
    public void uploadPayrolls(List<PaymentDTO> payrollDTO) {
        List<Payment> payroll = payrollDTO
                .stream()
                .map(modelMapper::mapToEntity)
                .collect(Collectors.toList());
        savePayments(payroll);
    }
    public Object getPaymentsByPeriod(String period, UserDetails userDetails) {
        if (period == null) {
            User user = userService.findUserByEmail(userDetails.getUsername());
            return user.getPayments()
                            .stream()
                            .sorted(Comparator.comparing(Payment::getPeriod).reversed())
                            .map(modelMapper::mapToDTO)
                            .collect(Collectors.toList());
        } else {
            Payment payment = getPayment(userDetails.getUsername(), period);
            return modelMapper.mapToDTO(payment);
        }
    }
}
