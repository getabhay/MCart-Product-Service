package com.nova.mcart.service.impl;

import com.nova.mcart.common.exception.ResourceNotFoundException;
import com.nova.mcart.dto.request.UserProfileUpsertRequest;
import com.nova.mcart.dto.request.UserSignupRequest;
import com.nova.mcart.dto.response.UserProfileResponse;
import com.nova.mcart.entity.User;
import com.nova.mcart.entity.enums.AuthProvider;
import com.nova.mcart.entity.enums.UserStatus;
import com.nova.mcart.repository.UserRepository;
import com.nova.mcart.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserProfileResponse signup(UserSignupRequest request) {
        String email = normalizeEmail(request.getEmail());

        User existing = userRepository
                .findByEmailIgnoreCaseAndProvider(email, request.getProvider())
                .orElse(null);

        if (existing != null) {
            existing.setCognitoSub(resolveCognitoSub(existing.getCognitoSub(), request.getCognitoSub()));
            existing.setLastLoginAt(LocalDateTime.now());
            return toResponse(userRepository.save(existing));
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setProvider(request.getProvider());
        user.setCognitoSub(resolveCognitoSub(null, request.getCognitoSub()));
        user.setEmailVerified(Boolean.TRUE.equals(request.getEmailVerified()));
        user.setLastLoginAt(LocalDateTime.now());
        user.setStatus(UserStatus.ACTIVE);

        return toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserProfileResponse upsertProfile(UserProfileUpsertRequest request) {
        String email = normalizeEmail(request.getEmail());

        User user = userRepository
                .findByEmailIgnoreCaseAndProvider(email, request.getProvider())
                .orElse(new User());

        if (user.getId() == null || user.getId() == 0L) {
           user = userRepository.findByCognitoSub(request.getCognitoSub()).orElse(new User());
        }

        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setProvider(request.getProvider());
        user.setCognitoSub(resolveCognitoSub(user.getCognitoSub(), request.getCognitoSub()));

        if (request.getEmailVerified() != null) {
            user.setEmailVerified(request.getEmailVerified());
        } else if (user.getEmailVerified() == null) {
            user.setEmailVerified(false);
        }

        user.setStatus(request.getStatus() != null ? request.getStatus() :
                (user.getStatus() != null ? user.getStatus() : UserStatus.ACTIVE));
        user.setLastLoginAt(LocalDateTime.now());

        return toResponse(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id=" + userId));
        return toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getByUserName(String userName) {
        User user = userRepository.findByCognitoSub(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with CognitoSub=" + userName));
        return toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getByEmail(String email, AuthProvider provider) {
        String normalizedEmail = normalizeEmail(email);

        if (provider != null) {
            User user = userRepository.findByEmailIgnoreCaseAndProvider(normalizedEmail, provider)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email=" + normalizedEmail + " and provider=" + provider));
            return toResponse(user);
        }

        List<User> users = userRepository.findAllByEmailIgnoreCase(normalizedEmail);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("User not found with email=" + normalizedEmail);
        }
        if (users.size() > 1) {
            throw new IllegalArgumentException("Multiple users found for email. Pass provider as query param.");
        }

        return toResponse(users.get(0));
    }

    private UserProfileResponse toResponse(User user) {
        UserProfileResponse out = new UserProfileResponse();
        out.setId(user.getId());
        out.setName(user.getName());
        out.setEmail(user.getEmail());
        out.setProvider(user.getProvider());
        out.setCognitoSub(user.getCognitoSub());
        out.setEmailVerified(user.getEmailVerified());
        out.setCreatedAt(user.getCreatedAt());
        out.setUpdatedAt(user.getUpdatedAt());
        out.setLastLoginAt(user.getLastLoginAt());
        out.setStatus(user.getStatus());
        return out;
    }

    private String normalizeEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        return email.trim().toLowerCase();
    }

    private String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String resolveCognitoSub(String existingCognitoSub, String requestCognitoSub) {
        String provided = trimToNull(requestCognitoSub);
        if (provided != null) {
            return provided;
        }

        String existing = trimToNull(existingCognitoSub);
        if (existing != null) {
            return existing;
        }

        return generateUniqueCognitoSub();
    }

    private String generateUniqueCognitoSub() {
        String candidate;
        do {
            candidate = UUID.randomUUID().toString();
        } while (userRepository.existsByCognitoSub(candidate));
        return candidate;
    }
}
