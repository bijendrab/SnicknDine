package com.wityo.modules.table.model;

public class RestaurantTable {


    private Long id;

    private Integer tableNumber;

    private Integer tableSize;

    private Integer qrCode;


    private Long restId;

    private boolean serviceChargeEnabled;
    private boolean packagingChargeEnabled;
    private boolean overAllDiscountEnabled;
    private float serviceCharge;
    private float packagingCharge;
    private float overallDiscount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getTableSize() {
        return tableSize;
    }

    public void setTableSize(Integer tableSize) {
        this.tableSize = tableSize;
    }

    public Integer getQrCode() {
        return qrCode;
    }

    public void setQrCode(Integer qrCode) {
        this.qrCode = qrCode;
    }

    public boolean isServiceChargeEnabled() {
        return serviceChargeEnabled;
    }

    public void setServiceChargeEnabled(boolean serviceChargeEnabled) {
        this.serviceChargeEnabled = serviceChargeEnabled;
    }

    public boolean isPackagingChargeEnabled() {
        return packagingChargeEnabled;
    }

    public void setPackagingChargeEnabled(boolean packagingChargeEnabled) {
        this.packagingChargeEnabled = packagingChargeEnabled;
    }

    public boolean isOverAllDiscountEnabled() {
        return overAllDiscountEnabled;
    }

    public void setOverAllDiscountEnabled(boolean overAllDiscountEnabled) {
        this.overAllDiscountEnabled = overAllDiscountEnabled;
    }

    public float getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(float serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public float getPackagingCharge() {
        return packagingCharge;
    }

    public void setPackagingCharge(float packagingCharge) {
        this.packagingCharge = packagingCharge;
    }

    public float getOverallDiscount() {
        return overallDiscount;
    }

    public void setOverallDiscount(float overallDiscount) {
        this.overallDiscount = overallDiscount;
    }

    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
    }
}
