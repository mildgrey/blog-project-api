package com.tal.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tal.blog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
