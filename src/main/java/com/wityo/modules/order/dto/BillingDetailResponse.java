package com.wityo.modules.order.dto;

import java.util.List;
import java.util.Map;

public class BillingDetailResponse {
    private List<BillingDetailItem> billingDetailItems;

    private List<TaxDetails> totalCalculatedTaxed;

    private List<DiscountDetails> totalCalculatedDiscount;

    private double serviceCharge;
    private float serviceChargePercent;
    private double addOnCharge;
    private float packagingCharge;
    private double overallDiscount;
    private float overallDiscountPercent;
    private double totalCostWithoutTaxAndDiscount;
    private double totalTax;
    private double totalItemsDiscount;
    private double totalCost;

    public List<BillingDetailItem> getBillingDetailItems() {
        return billingDetailItems;
    }

    public void setBillingDetailItems(List<BillingDetailItem> billingDetailItems) {
        this.billingDetailItems = billingDetailItems;
    }

    public List<TaxDetails> getTotalCalculatedTaxed() {
        return totalCalculatedTaxed;
    }

    public void setTotalCalculatedTaxed(List<TaxDetails> totalCalculatedTaxed) {
        this.totalCalculatedTaxed = totalCalculatedTaxed;
    }

    public List<DiscountDetails> getTotalCalculatedDiscount() {
        return totalCalculatedDiscount;
    }

    public void setTotalCalculatedDiscount(List<DiscountDetails> totalCalculatedDiscount) {
        this.totalCalculatedDiscount = totalCalculatedDiscount;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public float getServiceChargePercent() {
        return serviceChargePercent;
    }

    public void setServiceChargePercent(float serviceChargePercent) {
        this.serviceChargePercent = serviceChargePercent;
    }

    public double getAddOnCharge() {
        return addOnCharge;
    }

    public void setAddOnCharge(double addOnCharge) {
        this.addOnCharge = addOnCharge;
    }

    public float getPackagingCharge() {
        return packagingCharge;
    }

    public void setPackagingCharge(float packagingCharge) {
        this.packagingCharge = packagingCharge;
    }

    public double getOverallDiscount() {
        return overallDiscount;
    }

    public void setOverallDiscount(double overallDiscount) {
        this.overallDiscount = overallDiscount;
    }

    public float getOverallDiscountPercent() {
        return overallDiscountPercent;
    }

    public void setOverallDiscountPercent(float overallDiscountPercent) {
        this.overallDiscountPercent = overallDiscountPercent;
    }

    public double getTotalCostWithoutTaxAndDiscount() {
        return totalCostWithoutTaxAndDiscount;
    }

    public void setTotalCostWithoutTaxAndDiscount(double totalCostWithoutTaxAndDiscount) {
        this.totalCostWithoutTaxAndDiscount = totalCostWithoutTaxAndDiscount;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public double getTotalItemsDiscount() {
        return totalItemsDiscount;
    }

    public void setTotalItemsDiscount(double totalItemsDiscount) {
        this.totalItemsDiscount = totalItemsDiscount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
