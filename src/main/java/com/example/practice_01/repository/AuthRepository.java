package com.example.practice_01.repository;

import com.example.practice_01.common.Provider;
import com.example.practice_01.model.UserApp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends MongoRepository<UserApp, String> {
    Optional<UserApp> findByPhoneNumberAndProvider(String phoneNumber, Provider provider);
    Optional<UserApp> findByGmailAndProvider(String gmail, Provider provider);
    boolean existsByGmailAndProvider(String gmail, Provider provider);
    boolean existsByPhoneNumberAndProvider(String phoneNumber, Provider provider);

}
