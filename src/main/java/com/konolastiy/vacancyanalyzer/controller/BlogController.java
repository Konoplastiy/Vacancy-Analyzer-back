package com.konolastiy.vacancyanalyzer.controller;

import com.konolastiy.vacancyanalyzer.common.mapper.BlogMapper;
import com.konolastiy.vacancyanalyzer.entity.Blog;
import com.konolastiy.vacancyanalyzer.payload.blog.BlogDto;
import com.konolastiy.vacancyanalyzer.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/api/v1/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final BlogMapper blogMapper;

    @Operation(summary = "Creates a new blog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Blog is created"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<BlogDto> createPost(@Valid @RequestBody final BlogDto blogDto) {
        final Blog request = blogMapper.blogFromDto(blogDto);
        final Blog blog = blogService.savePost(request);
        final BlogDto response = blogMapper.blogToDto(blog);
        log.info("New Group with id: {} was crated", blog.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BlogDto>> findAllBlogs(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size
    ) {

        List<Blog> blog = blogService.findAllBlogs(PageRequest.of(page, size));
        List<BlogDto> response = blogMapper.listBlogsFromDto(blog);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
