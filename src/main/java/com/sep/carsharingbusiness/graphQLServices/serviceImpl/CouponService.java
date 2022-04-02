package com.sep.carsharingbusiness.graphQLServices.serviceImpl;

import com.sep.carsharingbusiness.graphQLServices.ICouponService;
import com.sep.carsharingbusiness.model.Coupon;
import com.sep.carsharingbusiness.mutations.MutationEnum;
import com.sep.carsharingbusiness.queries.QueryEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;

@Service
public class CouponService implements ICouponService {

    @SessionScope
    public Coupon getCoupon(String code) throws IOException, InterruptedException {
        return GraphQLService.createObjQuery(
                String.format(
                        GraphQLService.getQueryFromFile( QueryEnum.CouponByCode.get(), false),
                        code
                ),
                "coupon",
                Coupon.class
        );
    }

    @SessionScope
    public Coupon addCoupon(Coupon coupon) throws IOException, InterruptedException {
        return GraphQLService.createObjQuery(
                String.format(
                        GraphQLService.getQueryFromFile( MutationEnum.AddCoupon.get(), true),
                        coupon.getCode(),
                        coupon.getDiscount()
                ),
                "addCoupon",
                Coupon.class
        );
    }
}
