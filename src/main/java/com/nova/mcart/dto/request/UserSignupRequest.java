package com.nova.mcart.dto.request;

import com.nova.mcart.entity.enums.AuthProvider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequest {

    @NotBlank
    @Size(max = 255)
    private String name;

    @Email
    @NotBlank
    @Size(max = 255)
    private String email;

    @NotNull
    private AuthProvider provider;

    @Size(max = 255)
    private String cognitoSub;

    private Boolean emailVerified;
}
