package com.hiddenpath.guideservice.dto;

public class AvailabilityCheckResponse {

    private boolean available;

    public AvailabilityCheckResponse() {
    }

    public AvailabilityCheckResponse(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}