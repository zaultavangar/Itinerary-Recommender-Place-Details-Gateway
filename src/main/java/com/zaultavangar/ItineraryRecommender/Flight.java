package com.zaultavangar.ItineraryRecommender;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flight {
  private String GMT;

  private String codeIataAirport;

  private String codeIataCity;

  private String codeIcaoAiport;

  private Double distance;

  private Double latitudeAirport;

  private String codeIso2Country;

  private Double longitudeAirport;

  private String nameAirport;

  private String nameCountry;

  private String timezone;


}
