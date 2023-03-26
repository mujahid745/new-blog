package com.myblognew.services;

import java.util.List;

import com.myblognew.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(long postId, CommentDto commentDto);

	List<CommentDto> getCommentByPostId(long postId);

	CommentDto updateComment( long postId, long id, CommentDto commentDto);

}
