package com.zaultavangar.ItineraryRecommender;


import com.amadeus.Response;
import com.amadeus.resources.Location;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.amadeus.Amadeus;

@Service
public class FlightsService {

  public Object getNearestAirports(Double lat, Double lng) throws ResponseException {
    String apiKey = "d53d02-cead77";

    String apiUrl = "https://aviation-edge.com/v2/public/nearby";
    URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
        .queryParam("lat", lat)
        .queryParam("lng", lng)
        .queryParam("key", apiKey)
        .queryParam("distance", 100)
        .build()
        .toUri();
//
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

    System.out.println(responseEntity.getBody());
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      List<Flight> flightsResponse = objectMapper.readValue(
          responseEntity.getBody(), new TypeReference<List<Flight>>() {}
      );
      return flightsResponse;

    } catch (Exception e){
      throw new RuntimeException("Could not parse JSON response", e);
    }

  }
}
