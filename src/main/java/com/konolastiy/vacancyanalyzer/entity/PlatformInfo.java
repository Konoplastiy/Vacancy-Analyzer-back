package com.konolastiy.vacancyanalyzer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlatformInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String platformName;
  private Integer seniorCount;
  private Integer middleCount;
  private Integer juniorCount;
  private Integer otherCount;
}
