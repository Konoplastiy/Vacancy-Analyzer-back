package com.konolastiy.vacancyanalyzer.payload.blog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(
        name = "BlogResponse",
        description = "Schema to hold blog response information"
)
public class BlogDto {

    @NotBlank
    @Schema(description = "Title of the blog", example = "Sample Blog Title", required = true)
    private String title;

    @NotBlank
    @Schema(description = "Content of the blog", example = "Sample Blog Content", required = true)
    private String content;

    @NotBlank
    @Schema(description = "Image of the blog", example = "image.jpg", required = true)
    private String img;

    @NotNull
    @Schema(description = "Date when the blog was published", example = "2024-04-30", required = true)
    private LocalDate datePublic;

    @NotNull
    @PositiveOrZero
    @Schema(description = "Number of views for the blog", example = "100", required = true)
    private Integer countViews;
}
