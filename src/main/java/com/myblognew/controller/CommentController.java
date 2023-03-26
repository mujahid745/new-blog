package com.myblognew.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myblognew.payloads.CommentDto;
import com.myblognew.payloads.PostDto;
import com.myblognew.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	

    @PostMapping("posts/{postId}/comments")
	public ResponseEntity<CommentDto> saveComment(@Valid @PathVariable("postId") long postId, @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}
    
    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable("postId") long postId) {
    	List<CommentDto> dto = commentService.getCommentByPostId(postId);
    	return dto;
    }
    
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComments(  
    		
    		@PathVariable("postId") long postId,
    		@PathVariable("id") long id,
    		@RequestBody CommentDto commentDto
    		) {
    	
    	CommentDto dto = commentService.updateComment(postId, id, commentDto);
    	return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
