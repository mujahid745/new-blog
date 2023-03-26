package com.myblognew.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myblognew.payloads.PostDto;
import com.myblognew.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	private PostService postService;
	
	

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}


    @PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Object> savePost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		PostDto dto = postService.createPost(postDto);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	
	@GetMapping
	public List<PostDto> getPost(
			@RequestParam(value="pageNo", defaultValue="0", required=false) Integer pageNo,
			@RequestParam(value="pageSize", defaultValue="15", required=false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue="id", required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue="asc", required=false) String sortDir
			){
		 List<PostDto> dto = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
		 return dto;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getById(@PathVariable("id") long id) {
	return new ResponseEntity<>(postService.findById(id), HttpStatus.OK);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePosts(@PathVariable("id") long id, @RequestBody PostDto postDto) {
		PostDto dto = postService.updatePost(id, postDto);
		return new ResponseEntity<> (dto, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public void deletePosts(@PathVariable("id") long id) {
		postService.deletePost(id);
	}
	
}
