package com.wityo.modules.order.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Embeddable
public class TimeSpan {

    @Pattern(regexp = "([01]?[0-9]|2[0-3])[0-5][0-9]")
    private String startTime;
    @Pattern(regexp = "([01]?[0-9]|2[0-3])[0-5][0-9]")
    private String endTime;

    public TimeSpan() {
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
