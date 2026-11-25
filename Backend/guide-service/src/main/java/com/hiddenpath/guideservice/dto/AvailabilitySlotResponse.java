
package com.hiddenpath.guideservice.dto;

import com.hiddenpath.guideservice.enums.SlotStatus;
import java.time.LocalDateTime;

public class AvailabilitySlotResponse {

    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private SlotStatus status;

    public AvailabilitySlotResponse() {
    }

    public AvailabilitySlotResponse(
            Long id,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            SlotStatus status) {

        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public void setStatus(SlotStatus status) {
        this.status = status;
    }
}
