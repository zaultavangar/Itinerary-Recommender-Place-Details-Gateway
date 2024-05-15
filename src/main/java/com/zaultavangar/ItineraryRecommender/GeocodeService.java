package com.zaultavangar.ItineraryRecommender;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodeService {

  @Value("${mapbox.token}")
  private String token;

  public Object findLocations(String location){
    String apiUrl = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + location + ".json";
    URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
        .queryParam("access_token", token)
        .build()
        .toUri();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      FeatureCollection featureCollection = objectMapper.readValue(
          responseEntity.getBody(), FeatureCollection.class);
      List<Feature> features = featureCollection.getFeatures();
      if (!features.isEmpty()) {
        return features.get(0);
      } else {
        return "No features found";
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse JSON response", e);
    }

  }



}
