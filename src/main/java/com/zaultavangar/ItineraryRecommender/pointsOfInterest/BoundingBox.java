package com.zaultavangar.ItineraryRecommender.pointsOfInterest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoundingBox {
  private Float south;
  private Float west;
  private Float north;
  private Float east;
}
