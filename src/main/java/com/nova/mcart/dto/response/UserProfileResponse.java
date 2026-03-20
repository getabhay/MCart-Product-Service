package com.nova.mcart.dto.response;

import com.nova.mcart.entity.enums.AuthProvider;
import com.nova.mcart.entity.enums.UserStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {

    private Long id;
    private String name;
    private String email;
    private AuthProvider provider;
    private String cognitoSub;
    private Boolean emailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    private UserStatus status;
}
