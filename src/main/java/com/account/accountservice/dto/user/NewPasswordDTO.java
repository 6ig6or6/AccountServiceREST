package com.account.accountservice.dto.user;


import com.account.accountservice.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class NewPasswordDTO {

    @NotEmpty
    @ValidPassword
    @JsonProperty("new_password")
    private String newPassword;
}
