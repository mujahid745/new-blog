package com.myblognew.services;

import java.util.List;

import com.myblognew.payloads.PostDto;

public interface PostService {
	
	PostDto createPost(PostDto postDto);

	List<PostDto> getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

	PostDto updatePost(long id, PostDto postDto);

	PostDto findById(long id);

	void deletePost(long id);



}
