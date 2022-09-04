package com.account.accountservice.model.payment;


import com.account.accountservice.model.user.User;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "Payment")
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String email;

    @NonNull
    private LocalDate period;

    @NonNull
    private Long salary;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
