package com.nova.mcart.repository;

import com.nova.mcart.entity.User;
import com.nova.mcart.entity.enums.AuthProvider;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCaseAndProvider(String email, AuthProvider provider);

    List<User> findAllByEmailIgnoreCase(String email);
    Optional<User> findByCognitoSub(String userName);
    boolean existsByCognitoSub(String cognitoSub);
}
