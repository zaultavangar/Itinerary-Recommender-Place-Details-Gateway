package com.zaultavangar.ItineraryRecommender;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NerService {
    public List<String> getNERLocations(String input){
      String apiUrl = "https://api-inference.huggingface.co/models/dslim/bert-base-NER";
      String token = "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";
      String payload = "{\"inputs\":\"" + input + "\"}";

      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(token);
      headers.setContentType(MediaType.APPLICATION_JSON);

      RequestEntity<String> requestEntity = new RequestEntity<>(payload, headers, HttpMethod.POST,
          URI.create(apiUrl));

      RestTemplate restTemplate = new RestTemplate();

      ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

      ObjectMapper objectMapper = new ObjectMapper();
      try {
        List<NerEntity> nerEntities =  objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<NerEntity>>(){});
        return nerEntities.stream()
            .filter(nerEntity -> nerEntity.getScore() > 0.80 && nerEntity.getEntityGroup().equals("LOC"))
            .map(NerEntity::getWord)
            .toList();
      } catch (Exception e){
        throw new RuntimeException("Failed to parse JSON response", e);
      }
    }
}
