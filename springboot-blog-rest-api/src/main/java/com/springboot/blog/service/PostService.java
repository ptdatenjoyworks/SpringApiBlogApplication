package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPost(int pageNo, int pageSize,String sortBy,boolean sortType);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto, long id);
    String deletePost(long id);

    List<PostDto> findPostByCategoryId(Long categoryId);
}
