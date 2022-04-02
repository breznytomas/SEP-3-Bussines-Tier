package com.sep.carsharingbusiness.graphQLServices;

import com.sep.carsharingbusiness.model.Coupon;

import java.io.IOException;

public interface ICouponService {
    Coupon getCoupon(String code) throws IOException, InterruptedException;
    Coupon addCoupon(Coupon coupon) throws IOException, InterruptedException;
}
