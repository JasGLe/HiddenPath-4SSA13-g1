
package com.hiddenpath.booking.mapper;

import com.hiddenpath.booking.dto.BookingParticipantResponse;
import com.hiddenpath.booking.dto.BookingResponse;
import com.hiddenpath.booking.entity.Booking;
import com.hiddenpath.booking.entity.BookingParticipant;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookingMapper {

    private BookingMapper() {
        // Utility class: no instances needed
    }

    public static BookingResponse toResponse(Booking booking) {

        if (booking == null) {
            return null;
        }

        List<BookingParticipantResponse> participants =
                booking.getParticipants() == null
                        ? Collections.emptyList()
                        : booking.getParticipants()
                        .stream()
                        .map(BookingMapper::toParticipantResponse)
                        .collect(Collectors.toList());

        return new BookingResponse(
                booking.getId(),
                booking.getUserId(),
                booking.getExperienceId(),
                booking.getBookingDate(),
                booking.getNumberOfParticipants(),
                booking.getTotalPrice(),
                booking.getStatus(),
                participants
        );
    }

    private static BookingParticipantResponse toParticipantResponse(
            BookingParticipant participant) {

        if (participant == null) {
            return null;
        }

        return new BookingParticipantResponse(
                participant.getId(),
                participant.getFullName(),
                participant.getEmail()
        );
    }
}
