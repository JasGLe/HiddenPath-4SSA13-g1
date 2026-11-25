
package com.hiddenpath.guideservice.dto;

import com.hiddenpath.guideservice.enums.SlotStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AvailabilitySlotRequest {

    @NotNull(message = "Start date and time are required")
    private LocalDateTime startDateTime;

    @NotNull(message = "End date and time are required")
    private LocalDateTime endDateTime;

    @NotNull(message = "Slot status is required")
    private SlotStatus status;

    public AvailabilitySlotRequest() {
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
