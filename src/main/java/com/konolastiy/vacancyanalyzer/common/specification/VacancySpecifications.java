package com.konolastiy.vacancyanalyzer.common.specification;

import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class VacancySpecifications {

    public static Specification<Vacancy> hasTextInAttributes(String searchText) {
        return (root, query, builder) -> {
            if (searchText == null || searchText.isEmpty()) return null;
            return builder.or(
                    builder.like(root.get("vacancyName"), "%" + searchText + "%"),
                    builder.like(root.get("cityName"), "%" + searchText + "%"),
                    builder.like(root.get("companyName"), "%" + searchText + "%"),
                    builder.like(root.get("shortDescription"), "%" + searchText + "%"),
                    builder.like(root.get("experienceLevel"), "%" + searchText + "%")
            );
        };
    }

    public static Specification<Vacancy> hasExperienceLevel(String experienceLevel) {
        return (root, query, builder) ->
                experienceLevel == null ? null : builder.equal(root.get("experienceLevel"), experienceLevel);
    }

    public static Specification<Vacancy> hasSourceId(Integer sourceId) {
        return (root, query, builder) -> {
            if (sourceId == null) return null;
            Join<Vacancy, Source> sourceJoin = root.join("source");
            return builder.equal(sourceJoin.get("id"), sourceId);
        };
    }
}
