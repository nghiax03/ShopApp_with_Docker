package com.project.shopapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.models.CouponCondition;

public interface CouponConditionRepository extends JpaRepository<CouponCondition, Long> {
	List<CouponCondition> findByCouponId(long couponId);
}
