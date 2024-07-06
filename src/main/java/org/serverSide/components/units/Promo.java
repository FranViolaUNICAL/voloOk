package org.serverSide.components.units;

import java.util.Objects;

public class Promo implements Unit{
    private String description, code, endDate, origin, destination;
    private boolean fidelityOnly;

    private double discountFactor;

    public Promo(String description, String code, String endDate, String origin, String destination, boolean fidelityOnly, double discountFactor){
        this.description = description;
        this.code = code;
        this.endDate = endDate;
        this.origin = origin;
        this.destination = destination;
        this.fidelityOnly = fidelityOnly;
        this.discountFactor = discountFactor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean getFidelityOnly() { return fidelityOnly; }

    public double getDiscountFactor() { return discountFactor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promo promo = (Promo) o;
        return Objects.equals(code, promo.code) && Objects.equals(endDate, promo.endDate) && Objects.equals(origin, promo.origin) && Objects.equals(destination, promo.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
