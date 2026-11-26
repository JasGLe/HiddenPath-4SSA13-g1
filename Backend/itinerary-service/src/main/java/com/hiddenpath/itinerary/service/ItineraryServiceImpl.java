package com.hiddenpath.itinerary.service;

import com.hiddenpath.itinerary.dto.GenerateItineraryRequest;
import com.hiddenpath.itinerary.dto.ItineraryResponse;
import com.hiddenpath.itinerary.entity.Itinerary;
import com.hiddenpath.itinerary.entity.ItineraryStatus;
import com.hiddenpath.itinerary.repository.ItineraryRepository;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItineraryServiceImpl implements ItineraryService {

    private final ItineraryRepository itineraryRepository;

    public ItineraryServiceImpl(ItineraryRepository itineraryRepository) {
        this.itineraryRepository = itineraryRepository;
    }

    @Override
    public ItineraryResponse generateItinerary(GenerateItineraryRequest request) {

        Itinerary itinerary = new Itinerary();

        itinerary.setUserId(request.getUserId());
        itinerary.setDestination(request.getDestination());
        itinerary.setStartDate(request.getStartDate());
        itinerary.setEndDate(request.getEndDate());
        itinerary.setBudget(request.getBudget());
        itinerary.setStatus(ItineraryStatus.GENERATED);

        Itinerary savedItinerary = itineraryRepository.save(itinerary);

        ItineraryResponse response = new ItineraryResponse();

        response.setId(savedItinerary.getId());
        response.setUserId(savedItinerary.getUserId());
        response.setDestination(savedItinerary.getDestination());
        response.setStartDate(savedItinerary.getStartDate());
        response.setEndDate(savedItinerary.getEndDate());
        response.setBudget(savedItinerary.getBudget());
        response.setStatus(savedItinerary.getStatus());

        return response;
    }

    @Override
    public ItineraryResponse getItineraryById(UUID id) {

        Itinerary itinerary = itineraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerary not found"));

        ItineraryResponse response = new ItineraryResponse();

        response.setId(itinerary.getId());
        response.setUserId(itinerary.getUserId());
        response.setDestination(itinerary.getDestination());
        response.setStartDate(itinerary.getStartDate());
        response.setEndDate(itinerary.getEndDate());
        response.setBudget(itinerary.getBudget());
        response.setStatus(itinerary.getStatus());

        return response;
    }

    @Override
    public ItineraryResponse updateItinerary(
            UUID id,
            GenerateItineraryRequest request
    ) {

        Itinerary itinerary = itineraryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Itinerary not found"));

        itinerary.setUserId(request.getUserId());
        itinerary.setDestination(request.getDestination());
        itinerary.setStartDate(request.getStartDate());
        itinerary.setEndDate(request.getEndDate());
        itinerary.setBudget(request.getBudget());

        Itinerary updatedItinerary = itineraryRepository.save(itinerary);

        ItineraryResponse response = new ItineraryResponse();

        response.setId(updatedItinerary.getId());
        response.setUserId(updatedItinerary.getUserId());
        response.setDestination(updatedItinerary.getDestination());
        response.setStartDate(updatedItinerary.getStartDate());
        response.setEndDate(updatedItinerary.getEndDate());
        response.setBudget(updatedItinerary.getBudget());
        response.setStatus(updatedItinerary.getStatus());

        return response;
    }

    @Override
    public void deleteItinerary(UUID id) {

        Itinerary itinerary = itineraryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Itinerary not found"));

        itineraryRepository.delete(itinerary);
    }
}