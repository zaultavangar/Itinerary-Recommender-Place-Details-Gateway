package com.zaultavangar.ItineraryRecommender;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
  private String name;
  private CategoryIcon icon;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class CategoryIcon {
    @JsonProperty("created_at")
    private String createdAt;

    private String prefix;
    private String suffix;
    private Integer width;
    private Integer height;
  }

}
