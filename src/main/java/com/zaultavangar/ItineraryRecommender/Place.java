package com.zaultavangar.ItineraryRecommender;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {
  @JsonProperty("fsq_id")
  private String fsqId;

  private List<Category> categories;

  private String name;

  private Features features;

  private Geocodes geocodes;

  private Location location;

  private String description;

  private String website;

  private RelatedPlaces relatedPlaces;

  @JsonProperty("social_media")
  private Map<String, String> socialMedia;

  private Hours hours;

  @JsonProperty("hours_popular")
  private List<RegularHours> hoursPopular;

  private Double rating;

  private Stats stats;

  private Double popularity;

  private Integer price;

  private String menu;

  private List<Photo> photos;

  private List<Tip> tips;

  private List<String> tastes;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class Features {
    @JsonProperty("food_and_drink")
    private FoodAndDrink foodAndDrink;

    private Map<String, Object> amenities;

    private Map<String, String> attributes;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class FoodAndDrink {
      private Map<String, Boolean> alcohol;
      private Map<String, Boolean> meals;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class Location {
    private String address;
    private String postcode;
    private String locality;
    private String region;
    private String country;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  private static class RelatedPlaces {
    private List<Place> children;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class Hours {
    private List<RegularHours> regular;
  }
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class RegularHours {
    private String close;
    private Integer day;
    private String open;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class Stats {
    @JsonProperty("total_photos")
    private Integer totalPhotos;

    @JsonProperty("total_ratings")
    private Integer totalRatings;

    @JsonProperty("total_tips")
    private Integer totalTips;

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class Tip {
    @JsonProperty("created_at")
    private String createdAt;

    private String text;

    @JsonProperty("agree_count")
    private Integer agreeCount;

    @JsonProperty("disagree_count")
    private Integer disagreeCount;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class Photo {
    @JsonProperty("created_at")
    private String createdAt;

    private String prefix;

    private String suffix;

    private Integer width;

    private Integer height;

    private Tip tip;
  }



}
