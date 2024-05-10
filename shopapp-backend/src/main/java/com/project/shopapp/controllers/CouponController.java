package com.project.shopapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.responses.CouponCaculationResponse;
import com.project.shopapp.services.coupon.CouponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/coupons")
@RequiredArgsConstructor
public class CouponController {
	private final CouponService couponService;
	
	@GetMapping("/calculate")
	public ResponseEntity<CouponCaculationResponse> calculateCouponValue(
			@RequestParam("couponCode") String counponCode,
			@RequestParam("totalAmount") double totalAmount){
		try {
			double finalAmount = couponService.calculateCouponValue(counponCode, totalAmount);
			CouponCaculationResponse response = CouponCaculationResponse
					.builder()
					.result(finalAmount)
					.errorMessage("")
					.build();
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(
					CouponCaculationResponse
					.builder()
					.result(totalAmount)
					.errorMessage(e.getMessage())
					.build());
		}
	}
}
