package com.project.shopapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.models.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long>{
    Optional<Coupon> findByCode(String couponCode);
}
