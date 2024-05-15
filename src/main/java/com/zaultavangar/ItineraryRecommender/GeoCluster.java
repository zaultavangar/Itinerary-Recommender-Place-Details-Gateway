package com.zaultavangar.ItineraryRecommender;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeoCluster {
  private Integer k;
  private Map<String, Integer> y;
  private int[] size;
  private Double minPts;
  private Double radius;

}
