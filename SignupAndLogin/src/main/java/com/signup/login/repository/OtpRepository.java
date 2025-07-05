package com.signup.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.signup.login.entity.Otp;


public interface OtpRepository extends JpaRepository<Otp,Long>{
    Otp findTopByEmailOrderByCreatedAtDesc(String email);
}
 