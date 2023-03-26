package com.myblognew.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblognew.entities.Post;

public interface PostRepositories extends JpaRepository<Post, Long>{

}
