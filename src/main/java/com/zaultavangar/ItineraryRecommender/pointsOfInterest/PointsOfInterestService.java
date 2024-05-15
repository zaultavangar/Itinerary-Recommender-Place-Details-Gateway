package com.zaultavangar.ItineraryRecommender.pointsOfInterest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaultavangar.ItineraryRecommender.GeoCluster;
import com.zaultavangar.ItineraryRecommender.GeoClustering;
import com.zaultavangar.ItineraryRecommender.Geocodes;
import com.zaultavangar.ItineraryRecommender.Place;
import com.zaultavangar.ItineraryRecommender.PlaceSearchResponse;
import java.net.URI;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import smile.clustering.DBSCAN;
import smile.neighbor.lsh.Hash;


@Service
public class PointsOfInterestService {

  private GeoClustering geoClustering;

  private PlaceSearchResponse response;

  public PointsOfInterestService(GeoClustering geoClustering) {
    this.geoClustering = geoClustering;
    this.response = new PlaceSearchResponse();
  }

  private final Map<String, String> categoryMap =  Map.ofEntries(
      Map.entry("Amusement Park", "10001"),
      Map.entry("Aquarium", "10002"),
      Map.entry("Art Gallery", "10004"),
      Map.entry("Casino", "10008"),
      Map.entry("Museum", "10027"),
      Map.entry("Night Club", "10032"),
      Map.entry("Performing Arts Venue", "10035"),
      Map.entry("Stadium", "10051"),
      Map.entry("Library", "12080"),
      Map.entry("Spiritual Center", "12098"),
      Map.entry("Government Building", "12064"),
      Map.entry("Town Hall", "12124"),
      Map.entry("Outdoor Art Structure", "10069"),
      Map.entry("Art and Entertainment", "10000"),
      // Map.entry("Education", "12009"),
      // Map.entry("Drinking and Dining", "13000"),
      Map.entry("Landmarks and Outdoors", "16000"),
      // Map.entry("Retail", "17000"),
      Map.entry("Athletic Field", "18001")
      );


  public Object getFsqIds(List<Double> lngLat, List<String> categoryIds, Integer limit){
    String apiUrl = "https://api.foursquare.com/v3/places/search";
    String token = "fsq3LZC4gVbvg4ohjRBKGLWy2bbB//ZGXNCagWvV4mPzN8Q=";
    String fields = "fsq_id,name,geocodes,location,categories,timezone,description,website,social_media,hours,hours_popular,rating,stats,popularity,price,menu,photos,tips,tastes,features,store_id";

//    List<String> categoryIds = new ArrayList<>(categoryMap.values());
    String url = String.format("%s?ll=%f,%f&fields=%s&sort=popularity&limit=%s&categories=%s",
        apiUrl, lngLat.get(0), lngLat.get(1), fields, limit, String.join(",", categoryIds));

    URI uri = UriComponentsBuilder.fromHttpUrl(url)
        .build()
        .toUri();

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", token);

    RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      PlaceSearchResponse placeSearchResponse = objectMapper.readValue(responseEntity.getBody(), PlaceSearchResponse.class);
      return placeSearchResponse.getResults();
//      System.out.println("place search response: " + responseEntity.getBody());

//      Map<String, Object> resultMap = new HashMap<>();
////      resultMap.put("results", groupByCategory(placeSearchResponse));
//      resultMap.put("results", placeSearchResponse);
//      resultMap.put("clusters", clusterPlaces(placeSearchResponse));
//
//      return resultMap;


    } catch (Exception e) {
      throw new RuntimeException("Failed to parse JSON response", e);
    }
  }

  public GeoCluster getClusters() {
    if (this.response == null) return GeoCluster.builder()
        .k(0)
        .y(new HashMap<>())
        .size(new int[]{})
        .minPts(0d)
        .radius(0d)
        .build();
    return clusterPlaces(this.response);
  }

  public Map<String, List<Place>> groupByCategory(PlaceSearchResponse response) {
    List<Place> results = response.getResults();
    Map<String, List<Place>> grouped = results.stream().flatMap((place) -> place.getCategories().stream()
        .map((category) -> new AbstractMap.SimpleEntry<>(category.getName(), place)))
        .distinct()
        .collect(Collectors.groupingBy(Map.Entry::getKey,
            Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    return grouped;
  }

  public double[][] getPlaceCoordinates(PlaceSearchResponse response) {
    List<Place> places = response.getResults();
    double[][] coordinates = new double[places.size()][2];

    int i = 0;
    while (i < places.size()){
      Place place = places.get(i);
      Geocodes geocodes = place.getGeocodes();
      if (geocodes == null || geocodes.getMain() == null) continue;
      double latitude = geocodes.getMain().get("latitude");
      double longitude = geocodes.getMain().get("longitude");

      coordinates[i][0] = latitude;
      coordinates[i][1] = longitude;
      i++;
    }

    return coordinates;
  }

//  public double findOptimalRadius(double[][] coordinates){
//    double distances = 0;
//    double size = coordinates.length;
//    for (int i = 0; i < coordinates.length; i++){
//
//    }
//  }

  // I want to go through the clustered object, which has an array y. Each element in the array denotes the cluster group. I want to turn this into a map of place.fsq_id to the cluster group number

  public GeoCluster clusterPlaces(PlaceSearchResponse response) {
    double[][] coordinates = getPlaceCoordinates(response);
//    return coordinates;
    DBSCAN<double[]> clustered = geoClustering.cluster(coordinates, 0.5);
    Map<String, Integer> groupMap = new HashMap<>();
    List<Place> places = response.getResults();
    for (int i = 0; i < Math.min(places.size(), clustered.y.length); i++) {
      String fsqId = places.get(i).getFsqId();

      int clusterGroup = clustered.y[i];

      groupMap.put(fsqId, clusterGroup);
    }
    GeoCluster geoCluster = GeoCluster.builder()
        .k(clustered.k)
        .y(groupMap)
        .size(clustered.size)
        .minPts(clustered.minPts)
        .radius(clustered.radius)
        .build();
    return geoCluster;

  }


}
