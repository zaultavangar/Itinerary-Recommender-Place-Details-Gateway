package com.zaultavangar.ItineraryRecommender.pointsOfInterest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsOfInterestResponse {
  @JsonProperty("status_code")
  private Integer statusCode;

  @JsonProperty("server_timestamp")
  private Date serverTimestamp;

  private PlaceData data;
}
