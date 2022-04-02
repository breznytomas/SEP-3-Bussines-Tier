package com.sep.carsharingbusiness.logic;

import com.sep.carsharingbusiness.graphQLServices.ICouponService;
import com.sep.carsharingbusiness.graphQLServices.ILeaseService;
import com.sep.carsharingbusiness.logic.logicImpl.LeaseLogic;
import com.sep.carsharingbusiness.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class LeaseLogicTest {


    @MockBean
    private ILeaseService leaseService;
    @MockBean
    private ICouponService couponService;

    private ILeaseLogic leaseLogic;

    @BeforeEach
    void setUp() {
        leaseLogic = new LeaseLogic(leaseService, couponService);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    public void validateLease_SuccessWithCoupon() throws IOException, InterruptedException {
        Listing theListing = new Listing(
                1,
                LocalDateTime.of(2022, 1, 10, 12, 30, 0),
                new BigDecimal(100),
                "Aarhus",
                LocalDateTime.of(2022, 1, 1, 12, 30, 0),
                LocalDateTime.of(2022, 1, 31, 12, 30, 0),
                null
        );

        ArrayList<Lease> leasesByListing = new ArrayList<>();
        Lease leaseCanceled = new Lease(
                1,
                LocalDateTime.of(2022, 1, 17, 12, 30, 0),
                LocalDateTime.of(2022, 1, 22, 12, 30, 0),
                true,
                new BigDecimal(1500),
                theListing,
                new Customer("0202023344", "Mia", "White", "45 45 67 89", 0)
        );
        Lease leaseOtherInterval = new Lease(
                1,
                LocalDateTime.of(2022, 1, 28, 12, 30, 0),
                LocalDateTime.of(2022, 1, 30, 12, 30, 0),
                true,
                new BigDecimal(1500),
                theListing,
                new Customer("0303034455", "Jake", "Paul", "76 45 34 89", 0)
        );

        leasesByListing.add(leaseCanceled);
        leasesByListing.add(leaseOtherInterval);


        Lease leaseWithoutTotalPrice = new Lease(
                1,
                LocalDateTime.of(2022, 1, 20, 12, 30, 0),
                LocalDateTime.of(2022, 1, 25, 13, 30, 0),
                false,
                null,
                theListing,
                new Customer("0101012299", "Mike", "Long", "23 45 67 89", 0)
                );

        Coupon coupon = new Coupon("VIA2020", 0.1);

        // set up services
        given(this.couponService.getCoupon("VIA2022")).willReturn(coupon);
        given(this.leaseService.getLeasesByListing(1)).willReturn(leasesByListing);


        // call logic
        try {
            Lease leaseToTest = this.leaseLogic.validateLease(leaseWithoutTotalPrice, "VIA2022");

            // verify the price
            // 121h/24h * 100dkk * (1 - 0.1) = 453.75
            assertThat(leaseToTest.getTotalPrice()).isEqualTo(new BigDecimal("453.75"));
        }
        catch (IllegalArgumentException e) {
            // verify if lease date-time interval is valid because 1st lease is canceled, 2nd lease is on other date interval.
            assertThat(e).doesNotThrowAnyException();
        }

    }

    @Test
    public void validateLease_InvalidDateTimeInterval() throws IOException, InterruptedException {
        Listing theListing = new Listing(
                1,
                LocalDateTime.of(2022, 1, 10, 12, 30, 0),
                new BigDecimal(100),
                "Aarhus",
                LocalDateTime.of(2022, 1, 1, 12, 30, 0),
                LocalDateTime.of(2022, 1, 31, 12, 30, 0),
                new Vehicle("WW12345", "Tesla", "Model X", "SUV", "Automatic", "Electirc", 7, 2021, 1000, true, null)
        );

        ArrayList<Lease> leasesByListing = new ArrayList<>();
        Lease leaseSameInterval = new Lease(
                1,
                LocalDateTime.of(2022, 1, 17, 12, 30, 0),
                LocalDateTime.of(2022, 1, 22, 12, 30, 0),
                false,
                new BigDecimal(1500),
                theListing,
                new Customer("0202023344", "Mia", "White", "45 45 67 89", 0)
        );
        Lease leaseOtherInterval = new Lease(
                1,
                LocalDateTime.of(2022, 1, 28, 12, 30, 0),
                LocalDateTime.of(2022, 1, 30, 12, 30, 0),
                true,
                new BigDecimal(1500),
                theListing,
                new Customer("0303034455", "Jake", "Paul", "76 45 34 89", 0)
        );

        leasesByListing.add(leaseSameInterval);
        leasesByListing.add(leaseOtherInterval);


        Lease leaseWithoutTotalPrice = new Lease(
                1,
                LocalDateTime.of(2022, 1, 20, 12, 30, 0),
                LocalDateTime.of(2022, 1, 25, 13, 30, 0),
                false,
                null,
                theListing,
                new Customer("0101012299", "Mike", "Long", "23 45 67 89", 0)
        );

        Coupon coupon = new Coupon("VIA2020", 0.1);

        // set up services
        given(this.couponService.getCoupon("VIA2022")).willReturn(coupon);
        given(this.leaseService.getLeasesByListing(1)).willReturn(leasesByListing);


        // call logic

        try {
        Lease leaseToTest = this.leaseLogic.validateLease(leaseWithoutTotalPrice, "VIA2022");
        }
        catch (IllegalArgumentException e) {
            // verify if lease date-time interval is invalid because 1st lease has the same date-time interval.
            assertThat(e.getMessage()).startsWith("The listing of the vehicle with licenseNo of");
        }

    }
}
