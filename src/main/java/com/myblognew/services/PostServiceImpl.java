package com.myblognew.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.myblognew.entities.Post;
import com.myblognew.exceptions.ResourceNotFoundException;
import com.myblognew.payloads.PostDto;
import com.myblognew.repositories.PostRepositories;

@Service
public class PostServiceImpl implements PostService {
	
	private PostRepositories postRepo;
	@Autowired
	private ModelMapper modelMapper;
	

	public PostServiceImpl(PostRepositories postRepo) {
		super();
		this.postRepo = postRepo;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		
		Post post = mapToEntity(postDto);
		
		Post newPost = postRepo.save(post);
		
		
		PostDto dto = mapToDto(newPost);
		return dto;
	}

	PostDto mapToDto(Post post) {
		PostDto postDto = modelMapper.map(post , PostDto.class);
		
		/*
		 * PostDto postDto = new PostDto(); postDto.setId(post.getId());
		 * postDto.setTitle(post.getTitle());
		 * postDto.setDescription(post.getDescription());
		 * postDto.setContent(post.getContent());
		 */
		
		return postDto;
		
	}

	Post mapToEntity(PostDto postDto) {
		
		Post post = modelMapper.map(postDto, Post.class);
		/*
		 * Post post = new Post(); post.setTitle(postDto.getTitle());
		 * post.setDescription(postDto.getDescription());
		 * post.setContent(postDto.getContent());
		 */
		return post;
		
	}

	@Override
	public List<PostDto> getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
		// Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
         if(sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())) {
        	 Sort sort = Sort.by(sortBy).ascending();
        	 Pageable pageable= PageRequest.of(pageNo, pageSize, sort);
	       	 Page<Post> page = postRepo.findAll(pageable);
	       	 List<Post> posts = page.getContent();
		     List<PostDto> dto = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		     return dto;
	       	 
         } else {
        	 Sort sort = Sort.by(sortBy).descending();
        	 Pageable pageable= PageRequest.of(pageNo, pageSize, sort);
	       	 Page<Post> page = postRepo.findAll(pageable);
	       	 List<Post> posts = page.getContent();
		     List<PostDto> dto = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		     return dto;
         }
		 
		
	     
		
	}

	@Override
	public PostDto updatePost(long id, PostDto postDto) {
		Post post = postRepo.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("post", "id", id)
				);
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getTitle());
		post.setContent(postDto.getDescription());
		Post post1 = postRepo.save(post);
		return mapToDto(post1);
	}

	@Override
	public PostDto findById(long id) {
		Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", id));
		PostDto dto = mapToDto(post);
		return dto;
	}

	@Override
	public void deletePost(long id) {
		Post post = postRepo.findById(id).get();
		postRepo.delete(post);
	}


}
