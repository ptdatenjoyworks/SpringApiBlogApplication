package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private ModelMapper mapper;


    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }


    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        var comment = mappingToComment(postId, commentDto);
        commentRepository.save(comment);
        var newCommentDto = mappingToCommentDto(comment);
        return newCommentDto;
    }

    @Override
    public List<CommentDto> getALlCommentByPostId(Long postId) {

        List<Comment> comments = commentRepository.getListCommentByPostId(postId);
        if (!comments.isEmpty()) {
            List<CommentDto> commentDtos = new ArrayList<>();
            commentDtos = comments.stream().map(this::mappingToCommentDto).collect(Collectors.toList());

            return commentDtos;
        }
        throw new ResourceNotFoundException("Post comments", "id", Long.toString(postId));

    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        List<Comment> comments = commentRepository.getListCommentByPostId(postId);
        var comment = comments.stream().filter(cm -> cm.getId() == commentId).findFirst().orElseThrow(() -> new ResourceNotFoundException("comment", "id", Long.toString(commentId)));
        var commentDto = mappingToCommentDto(comment);
       /* if(!comment.getPost().getId().equals(postRepository.findById(postId).orElseThrow().getId())){
            throw new BlogAPIException();
        }*/
        return commentDto;
    }

    @Override
    public CommentDto updateComment(Long postId, CommentDto commentDto) {
        var post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));

        var comment = commentRepository.findById(commentDto.getId()).orElseThrow(() -> new BlogAPIException("Comment not exist", HttpStatus.NOT_FOUND, ""));
        comment.setBody(commentDto.getBody());
        commentRepository.save(comment);
        return mappingToCommentDto(comment);
    }

    @Override
    public String deleteComment(Long postId, Long commentId) {
        var post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));
        var comment = commentRepository.findById(commentId).orElseThrow(() -> new BlogAPIException("Comment not exist", HttpStatus.NOT_FOUND, ""));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment not belong to post");
        }
        commentRepository.delete(comment);
        return "Comment delete success!";
    }

    public Comment mappingToComment(Long posId, CommentDto commentDto) {
        Comment comment = mapper.map(commentDto,Comment.class);
        return comment;
    }

    public CommentDto mappingToCommentDto(Comment comment) {
        CommentDto commentDto = mapper.map(comment,CommentDto.class);
        return commentDto;
    }
}
