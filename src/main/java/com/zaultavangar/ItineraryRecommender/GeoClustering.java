package com.zaultavangar.ItineraryRecommender;

import smile.clustering.DBSCAN;
import org.springframework.stereotype.Service;
import smile.math.distance.ChebyshevDistance;

@Service
public class GeoClustering {

  // radius in kilometers
  public DBSCAN<double[]> cluster(double[][] coordinates, double radius) {
    HaversineDistance haversineDistance = new HaversineDistance();
    // DBSCAN parameters
    int minPts = 1;
    return DBSCAN.fit(coordinates, haversineDistance, minPts, radius);
  }

}
