package com.myblognew.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myblognew.entities.Comment;
import com.myblognew.entities.Post;
import com.myblognew.exceptions.ResourceNotFoundException;
import com.myblognew.payloads.CommentDto;
import com.myblognew.repositories.CommentRepository;
import com.myblognew.repositories.PostRepositories;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private PostRepositories postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
	    Post post = postRepo.findById(postId).orElseThrow(
	    		()->new ResourceNotFoundException("post", "id", postId)
	    		);
	    Comment comment = mapToComment(commentDto);
	    comment.setPost(post);
	    Comment newComment = commentRepo.save(comment);
	    
	    CommentDto commentDto1 = mapToCommentDto(newComment);
		return commentDto1;
	}

	  CommentDto mapToCommentDto(Comment comment) {
		  
		  CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
			/*
			 * CommentDto commentDto = new CommentDto();
			 * 
			 * commentDto.setId(comment.getId()); commentDto.setBody(comment.getBody());
			 * commentDto.setEmail(comment.getEmail());
			 * commentDto.setName(comment.getEmail());
			 */
		return commentDto;
	}

	Comment mapToComment(CommentDto commentDto) {
		
		Comment comment = modelMapper.map(commentDto, Comment.class);
		/*
		 * Comment comment = new Comment(); comment.setBody(commentDto.getBody());
		 * comment.setEmail(commentDto.getEmail());
		 * comment.setName(commentDto.getName());
		 */
		return comment;
	}

	@Override
	public List<CommentDto> getCommentByPostId(long postId) {
		List<Comment> comments = commentRepo.findByPostId(postId);
		List<CommentDto> dto = comments.stream().map(x->mapToCommentDto(x)).collect(Collectors.toList());
		return dto;
	}

	@Override
	public CommentDto updateComment(long id, long postId, CommentDto commentDto) {
		Post post = postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("post", "postId", postId)
				);
		
		Comment comment = commentRepo.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("comment", "id", id)
				);
		
		
		
		comment.setBody(commentDto.getBody());
		comment.setEmail(commentDto.getEmail());
		comment.setName(commentDto.getName());
		
		Comment updateComment = commentRepo.save(comment);
		
		CommentDto dto = mapToCommentDto(updateComment);
		return dto;
	}

}
