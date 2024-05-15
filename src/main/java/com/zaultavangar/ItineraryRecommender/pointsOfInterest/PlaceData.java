package com.zaultavangar.ItineraryRecommender.pointsOfInterest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceData {
  private String id;

  private Level level;

  private List<Category> categories;

  private Float rating;

  @JsonProperty("rating_local")
  private Float localRating;

  private String quadKey;

  private Location location;

  @JsonProperty("bounding_box")
  private BoundingBox bbox;

  private String name;

  @JsonProperty("name_suffix")
  private String nameSuffix;

  private String originalName;

  private String perex;

  private String url;

  @JsonProperty("thumbnail_url")
  private String thumbnailUrl;

  private String marker;

  private List<String> parentIds;

  @JsonProperty("star_rating")
  private Float starRating;

  @JsonProperty("star_rating_unofficial")
  private Float starRatingUnofficial;

  @JsonProperty("customer_rating")
  private Float customerRating;

  private Integer duration;

  public enum Level {
    CONTINENT, COUNTRY, STATE, REGION, COUNTY, CITY, TOWN, VILLAGE, SETTLEMENT, LOCALITY, NEIGHBOURHOOD, ARCHIPELAGO, ISLAND, POI;

    @JsonCreator
    public static Level forValue(String value) {
      return Level.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
      return this.name().toLowerCase();
    }
  }

  public enum Category {
    DISCOVERING, EATING, GOING_OUT, HIKING, PLAYING, RELAXING, SHOPPING, SIGHTSEEING, SLEEPING, DOING_SPORTS, TRAVELING;

    @JsonCreator
    public static Category forValue(String value) {
      return Category.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
      return this.name().toLowerCase();
    }
  }

}
