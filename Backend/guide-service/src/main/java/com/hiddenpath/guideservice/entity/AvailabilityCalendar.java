
package com.hiddenpath.guideservice.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "availability_calendars")
public class AvailabilityCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "guide_id", nullable = false, unique = true)
    private Guide guide;

    @OneToMany(
            mappedBy = "calendar",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AvailabilitySlot> slots = new ArrayList<>();

    public AvailabilityCalendar() {
    }

    public Long getId() {
        return id;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public List<AvailabilitySlot> getSlots() {
        return slots;
    }

    public void setSlots(List<AvailabilitySlot> slots) {
        this.slots.clear();

        if (slots != null) {
            for (AvailabilitySlot slot : slots) {
                addSlot(slot);
            }
        }
    }

    public void addSlot(AvailabilitySlot slot) {
        if (slot != null) {
            slots.add(slot);
            slot.setCalendar(this);
        }
    }

    public void removeSlot(AvailabilitySlot slot) {
        if (slots.remove(slot)) {
            slot.setCalendar(null);
        }
    }
}
