package com.konolastiy.vacancyanalyzer.service.blog;

import com.konolastiy.vacancyanalyzer.entity.Blog;
import com.konolastiy.vacancyanalyzer.repository.BlogRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlogService {

  private final BlogRepository blogRepository;

  @Transactional
  public Blog savePost(@NonNull final Blog blog) {
    return blogRepository.save(blog);
  }

  @Transactional(readOnly = true)
  public Page<Blog> findAllBlogs(final Pageable pageable) {
    return blogRepository.findAll(pageable);
  }
}
