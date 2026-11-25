
package com.hiddenpath.guideservice.entity;

import com.hiddenpath.guideservice.enums.SlotStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "availability_slots")
public class AvailabilitySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private SlotStatus status;

    @ManyToOne
    @JoinColumn(name = "calendar_id", nullable = false)
    private AvailabilityCalendar calendar;

    public AvailabilitySlot() {
    }

    public Long getId() {
        return id;
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

    public AvailabilityCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(AvailabilityCalendar calendar) {
        this.calendar = calendar;
    }
}
