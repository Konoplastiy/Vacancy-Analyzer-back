package com.konolastiy.vacancyanalyzer.service;

import com.konolastiy.vacancyanalyzer.entity.Blog;
import com.konolastiy.vacancyanalyzer.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BlogServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogService blogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenSavePost_thenCorrectBlogIsSaved() {
        Blog blog = new Blog();
        when(blogRepository.save(any(Blog.class))).thenReturn(blog);

        Blog savedBlog = blogService.savePost(blog);

        assertEquals(blog, savedBlog);
        verify(blogRepository).save(blog);
    }

    @Test
    void whenFindAllBlogs_withPageable_thenPageIsReturned() {
        Pageable pageable = Pageable.unpaged();
        Blog blog = new Blog();
        Page<Blog> expectedPage = new PageImpl<>(Arrays.asList(blog));

        when(blogRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Blog> resultPage = blogService.findAllBlogs(pageable);

        assertEquals(expectedPage, resultPage);
        verify(blogRepository).findAll(pageable);
    }
}
