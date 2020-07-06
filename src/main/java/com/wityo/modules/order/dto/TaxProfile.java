package com.wityo.modules.order.dto;

public class TaxProfile {
    private Long taxProfileId;
    private String taxProfileName;
    private String taxType;
    private float taxPercent;
    private String appliedOn;

    public Long getTaxProfileId() {
        return taxProfileId;
    }

    public void setTaxProfileId(Long taxProfileId) {
        this.taxProfileId = taxProfileId;
    }

    public String getTaxProfileName() {
        return taxProfileName;
    }

    public void setTaxProfileName(String taxProfileName) {
        this.taxProfileName = taxProfileName;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public float getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(float taxPercent) {
        this.taxPercent = taxPercent;
    }

    public String getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(String appliedOn) {
        this.appliedOn = appliedOn;
    }
}
