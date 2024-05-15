package com.zaultavangar.ItineraryRecommender;

import java.util.Arrays;
import smile.math.distance.Distance;

/*
  Measure of the great-circle distance between two points on a sphere given their longitudes
  and latitudes
 */

public class HaversineDistance implements Distance<double[]> {

  private static final double R = 6371; // Earth radius in km

  @Override
  public double d(double[] x, double[] y){
    System.out.println("x: "+ Arrays.toString(x));
    System.out.println("y: " + Arrays.toString(y));
    double dLat = Math.toRadians(y[0] - x[0]);
    double dLon = Math.toRadians(y[1] - x[1]);
    double lat1 = Math.toRadians(x[0]);
    double lat2 = Math.toRadians(y[0]);

    double a = Math.pow(Math.sin(dLat / 2.0), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon/ 2.0), 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    System.out.print("Distance: " + (R*c) + "km");
    return R * c;

  }

}
