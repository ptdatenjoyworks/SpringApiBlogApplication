package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/{post_id}/comments")
public class CommentController {


    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable("post_id") long postId,@Valid @RequestBody CommentDto commentDto) {
        var newcommentDto = commentService.createComment(postId, commentDto);

        return ResponseEntity.ok(newcommentDto);
    }

    @GetMapping("/getComments")
    public ResponseEntity<List<CommentDto>> getAllCommentByPostId(@PathVariable("post_id") long postId) {

        var listComment = commentService.getALlCommentByPostId(postId);

        return ResponseEntity.ok(listComment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("post_id") long postId, @PathVariable("id") long commentId) {
        var commentDto = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(commentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@Valid @PathVariable("post_id") long postId,@Valid @RequestBody() CommentDto commentDto) {
        var newCommentDto = commentService.updateComment(postId, commentDto);
        return ResponseEntity.ok(commentDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("post_id") long postId, @PathVariable("id") long commentId){
        var result = commentService.deleteComment(postId,commentId);
        return ResponseEntity.ok(result);
    }

}
