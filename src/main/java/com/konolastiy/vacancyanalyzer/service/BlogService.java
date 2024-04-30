package com.konolastiy.vacancyanalyzer.service;

import com.konolastiy.vacancyanalyzer.entity.Blog;
import com.konolastiy.vacancyanalyzer.repository.BlogRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    public Blog savePost(@NonNull final Blog blog) {
        return blogRepository.save(blog);
    }

    @Transactional(readOnly = true)
    public List<Blog> findAllBlogs(final PageRequest pageRequest) {
       Page<Blog> page = blogRepository.findAll(pageRequest);
       return page.getContent();
    }
}
