package com.zaultavangar.ItineraryRecommender;

import com.zaultavangar.ItineraryRecommender.pointsOfInterest.PointsOfInterestService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = {"http://localhost:1050", "https://browndash.com"})
public class ItineraryRecommenderController {
//  public NerService nerService;
  public GeocodeService geocodeService;

  public FlightsService flightsService;

  public PointsOfInterestService pointsOfInterestService;



  public ItineraryRecommenderController(
//      NerService nerService,
      GeocodeService geocodeService,
      FlightsService flightsService,
      PointsOfInterestService pointsOfInterestService){
//    this.nerService = nerService;
    this.geocodeService = geocodeService;
    this.flightsService = flightsService;
    this.pointsOfInterestService = pointsOfInterestService;
  }

//  @GetMapping("/identifyLocations")
//  public Object identifyLocations(@RequestParam String input){
//
//    return nerService.getNERLocations(input);
//  }

  @GetMapping("/geocodeLocation")
  public Object geocodeLocation(@RequestParam String input){
    return geocodeService.findLocations(input);
  }

//  @GetMapping("/nearestAirports")
//  public Object nearestAirports(@RequestParam String lat, @RequestParam String lng){
//    try {
//      return flightsService.getNearestAirports(
//          Double.parseDouble(lat),
//          Double.parseDouble(lng));
//    } catch (Exception err) {
//      return Map.of("error: ", err);
//    }
//  }

  @GetMapping("/placeDetails")
  public Object getPlaceDetailsByLngLat(
      @RequestParam String lat,
      @RequestParam  String lng,
      @RequestParam List<String> categoryIds,
      @RequestParam Integer limit
  ){
    return pointsOfInterestService.getFsqIds(
        List.of(
          Double.parseDouble(lat),
          Double.parseDouble(lng)
        ),
        categoryIds,
        limit
    );
  }

  @GetMapping("/clusters")
  public Object getClusters(){
    return pointsOfInterestService.getClusters();
  }

}
