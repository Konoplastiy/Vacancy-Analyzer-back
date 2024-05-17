package com.konolastiy.vacancyanalyzer.controller;

import com.konolastiy.vacancyanalyzer.common.mapper.BlogMapper;
import com.konolastiy.vacancyanalyzer.entity.Blog;
import com.konolastiy.vacancyanalyzer.payload.blog.BlogDto;
import com.konolastiy.vacancyanalyzer.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.BlogControllerTest.TEST_BLOG;
import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.BlogControllerTest.TEST_BLOG_DTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BlogControllerTest {

    @Mock
    private BlogService blogService;

    @Mock
    private BlogMapper blogMapper;

    @InjectMocks
    private BlogController blogController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createPost_WhenValidBlog_ReturnsCreatedStatus() {
        when(blogMapper.blogFromDto(any(BlogDto.class))).thenReturn(TEST_BLOG);
        when(blogService.savePost(any(Blog.class))).thenReturn(TEST_BLOG);
        when(blogMapper.blogToDto(any(Blog.class))).thenReturn(TEST_BLOG_DTO);

        ResponseEntity<BlogDto> response = blogController.createPost(TEST_BLOG_DTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(TEST_BLOG_DTO, response.getBody());
        verify(blogService).savePost(TEST_BLOG);
        verify(blogMapper).blogToDto(TEST_BLOG);
    }

    @Test
    void createPost_WhenServerError_ThrowsInternalServerError() {
        when(blogMapper.blogFromDto(any(BlogDto.class))).thenReturn(TEST_BLOG);
        when(blogService.savePost(any(Blog.class))).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> blogController.createPost(TEST_BLOG_DTO));
        verify(blogMapper).blogFromDto(TEST_BLOG_DTO);
        verify(blogService).savePost(TEST_BLOG);
    }

    @Test
    void findAllBlogs_WhenCalled_ReturnsBlogsPage() {
        Page<BlogDto> page = new PageImpl<>(Collections.singletonList(TEST_BLOG_DTO));
        when(blogService.findAllBlogs(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(TEST_BLOG)));
        when(blogMapper.blogToDto(any(Blog.class))).thenReturn(TEST_BLOG_DTO);
        ResponseEntity<Page<BlogDto>> response = blogController.findAllBlogs(Pageable.unpaged());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
        assertTrue(response.getBody().getContent().contains(TEST_BLOG_DTO));
    }

}