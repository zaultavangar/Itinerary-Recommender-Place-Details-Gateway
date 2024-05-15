package com.zaultavangar.ItineraryRecommender.pointsOfInterest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
  private Float lat;
  private Float lng;
}
