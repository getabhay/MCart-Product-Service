package com.nova.mcart.dto.request;

import com.nova.mcart.entity.enums.AuthProvider;
import com.nova.mcart.entity.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileUpsertRequest {

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

    private UserStatus status;
}
