package com.nova.mcart.service;

import com.nova.mcart.dto.request.UserProfileUpsertRequest;
import com.nova.mcart.dto.request.UserSignupRequest;
import com.nova.mcart.dto.response.UserProfileResponse;
import com.nova.mcart.entity.enums.AuthProvider;

public interface UserService {

    UserProfileResponse signup(UserSignupRequest request);

    UserProfileResponse upsertProfile(UserProfileUpsertRequest request);

    UserProfileResponse getByUserId(Long userId);

    UserProfileResponse getByUserName(String userName);

    UserProfileResponse getByEmail(String email, AuthProvider provider);
}
