package com.sep.carsharingbusiness.logic.logicImpl;

import com.sep.carsharingbusiness.graphQLServices.ICouponService;
import com.sep.carsharingbusiness.graphQLServices.ILeaseService;
import com.sep.carsharingbusiness.logic.ILeaseLogic;
import com.sep.carsharingbusiness.model.Coupon;
import com.sep.carsharingbusiness.model.Lease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Service
public class LeaseLogic implements ILeaseLogic {
    private final ICouponService couponService;
    private final ILeaseService leaseService;
    private final DateTimeFormatter formatter;

    @Autowired
    public LeaseLogic(ILeaseService leaseService, ICouponService couponService) {
        this.leaseService = leaseService;
        this.couponService = couponService;
        this.formatter = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm");
    }

    @SessionScope
    public Lease getLeaseById(int id) throws IOException, InterruptedException {
        return leaseService.getLeaseById(id);
    }

    @SessionScope
    public ArrayList<Lease> getLeasesByListing(int listingId) throws IOException, InterruptedException {
        return leaseService.getLeasesByListing(listingId);
    }

    @SessionScope
    public ArrayList<Lease> getLeasesByCustomer(String cpr) throws IOException, InterruptedException {
        return leaseService.getLeasesByCustomer(cpr);
    }


    @SessionScope
    public Lease addLease(Lease lease) throws IOException, InterruptedException, IllegalArgumentException {
        return leaseService.addLease(lease);
    }

    private boolean isValidLeasingDates(Lease lease, Lease newLease) {
        return newLease.getLeasedTo().isBefore(lease.getLeasedFrom())
                || newLease.getLeasedFrom().isAfter(lease.getLeasedTo());
    }

    private boolean validateLease(Lease lease) throws IOException, InterruptedException {
        // TODO: 10.12.2021 by Ion - change the query to only get the leases for this listing and these dates (optimization)
        ArrayList<Lease> leasesForThisListing = leaseService.getLeasesByListing(lease.listing.getId());
        for (Lease l : leasesForThisListing) {
            if (l.isCanceled()) continue;
            if (!isValidLeasingDates(l, lease)) {
                throw new IllegalArgumentException(
                        String.format(
                                "The listing of the vehicle with licenseNo of %s is not available in the interval of '%s' and '%s'",
                                lease.listing.vehicle.getLicenseNo(),
                                lease.getLeasedFrom().format(formatter),
                                lease.getLeasedTo().format(formatter)
                        ));
            }
        }
        return true;
    }

    @SessionScope
    public Lease validateLease(Lease lease, String couponCode) throws IOException, InterruptedException, IllegalArgumentException, InternalError {
        double discount = 0;
        if (!couponCode.isEmpty() && !couponCode.equals("default")) {
            Coupon coupon = couponService.getCoupon(couponCode);
            discount = coupon.getDiscount();
        }

        validateLease(lease);

        //calculate the total price by hours and coupon discount;
        long hoursBetween = ChronoUnit.HOURS.between(lease.getLeasedFrom(), lease.getLeasedTo());
        double price = (hoursBetween * lease.listing.getPrice().longValue()) / 24D;
        double totalPrice = price * (1 - discount);

        BigDecimal leaseTotalPrice = new BigDecimal(totalPrice);
        leaseTotalPrice = leaseTotalPrice.setScale(2, RoundingMode.CEILING);

        lease.setTotalPrice(leaseTotalPrice);
        return lease;
    }

    @SessionScope
    public boolean cancelLease(int id) throws IOException, InterruptedException {
        try {
            Lease lease = leaseService.getLeaseById(id);
            lease.setCanceled(true);
            leaseService.updateLease(lease);
            return true;
        } catch (InternalError e) {
            return false;
        }

    }
}
