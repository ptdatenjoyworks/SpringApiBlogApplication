package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService
{
    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> getALlCommentByPostId(Long postId);
    CommentDto getCommentById (Long postId, Long commentId);
    CommentDto updateComment(Long postId,CommentDto commentDto);
    String deleteComment(Long postId,Long commentId);
}
