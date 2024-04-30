package com.konolastiy.vacancyanalyzer.common.mapper;

import com.konolastiy.vacancyanalyzer.entity.Blog;
import com.konolastiy.vacancyanalyzer.payload.blog.BlogDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BlogMapper {

    Blog blogFromDto(BlogDto blogDto);

    BlogDto blogToDto(Blog blog);
}
