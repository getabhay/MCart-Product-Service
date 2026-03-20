package com.nova.mcart.controller;

import com.nova.mcart.dto.request.UserProfileUpsertRequest;
import com.nova.mcart.dto.request.UserSignupRequest;
import com.nova.mcart.dto.response.UserProfileResponse;
import com.nova.mcart.entity.enums.AuthProvider;
import com.nova.mcart.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserProfileResponse> signup(@Valid @RequestBody UserSignupRequest request) {
        return ResponseEntity.ok(userService.signup(request));
    }

    @PostMapping("/profile")
    public ResponseEntity<UserProfileResponse> createOrUpdateProfile(@Valid @RequestBody UserProfileUpsertRequest request) {
        return ResponseEntity.ok(userService.upsertProfile(request));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileResponse> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getByUserId(userId));
    }

    @GetMapping("/profile/cognito/{userName}")
    public ResponseEntity<UserProfileResponse> getByUserName(@PathVariable String userName) {
        return ResponseEntity.ok(userService.getByUserName(userName));
    }

    @GetMapping("/profile/by-email")
    public ResponseEntity<UserProfileResponse> getByEmail(
            @RequestParam String email,
            @RequestParam(required = false) AuthProvider provider
    ) {
        return ResponseEntity.ok(userService.getByEmail(email, provider));
    }
}
