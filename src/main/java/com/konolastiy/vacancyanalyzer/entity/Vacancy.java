package com.konolastiy.vacancyanalyzer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.Length;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Vacancy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vacancyName;
    private String cityName;

    private String date;

    private String companyName;

    @Length(max = 400)
    private String shortDescription;

    private String urlVacancy;

    private String salary;

    private String experienceLevel;

    private String englishLevel;

    private String programmingLanguage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    @NotAudited
    private Source source;

    @Column(name = "source_id", insertable = false, updatable = false)
    private Integer source_id;
}