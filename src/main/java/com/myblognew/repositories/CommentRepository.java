package com.myblognew.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblognew.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findByPostId(long postId);

}
