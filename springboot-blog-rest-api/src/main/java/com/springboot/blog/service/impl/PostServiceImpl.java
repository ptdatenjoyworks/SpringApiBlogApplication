package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;
    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper mapper,
                           CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public PostDto createPost(PostDto postDto) {


        //Check category exists
        var category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", Long.toString(postDto.getCategoryId())));

        //convert DTO to entity
        Post post = mappingPostEntity(postDto);
        post.setCategory(category);
        var newPost = postRepository.save(post);

        // convert entity to Dto
        PostDto postResponse = mappingPostDto(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, boolean sortType) {

        //create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortType ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<Post> posts = postRepository.findAll(pageable);

        // get content for page object

        List<Post> postList = posts.getContent();

        List<PostDto> postsDto = postList.stream().map(post -> mappingPostDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postsDto);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));

        return mappingPostDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {

        var category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", Long.toString(postDto.getCategoryId())));
        var post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));
        if (post != null) {
            post.setTitle(postDto.getTitle());
            post.setDescription(postDto.getDescription());
            post.setContent(postDto.getContent());
            post.setCategory(category);
            Post updatePost = postRepository.save(post);

            return mappingPostDto(updatePost);
        }
        return null;
    }

    @Override
    public String deletePost(long id) {
        var post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));
        if (post != null) {
            postRepository.delete(post);
            return "Post had been delete";
        }
        return "delete post fail";
    }

    @Override
    public List<PostDto> findPostByCategoryId(Long categoryId) {

        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", Long.toString(categoryId)));

        var posts = postRepository.findPostsByCategoryId(categoryId)
                .stream()
                .map((post) -> mapper.map(post, PostDto.class)).collect(Collectors.toList());

        return posts;
    }

    //convert Entity to DTO
    public PostDto mappingPostDto(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }

    //convert Dto to Entity
    public Post mappingPostEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        return post;
    }
}
