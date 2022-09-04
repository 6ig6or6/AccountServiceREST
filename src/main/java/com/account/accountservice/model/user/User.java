package com.account.accountservice.model.user;

import com.account.accountservice.model.payment.Payment;
import com.account.accountservice.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity(name = "User")
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String lastname;

    @NotEmpty
    @Email(regexp = "\\w+(@acme.com)$")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    private final Set<Role> roles = new HashSet<>();

    @NotEmpty
    @ValidPassword
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Payment> payments;

    private boolean isAccountNonLocked;

    private int failedLoginAttempts;
}
