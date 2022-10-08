package com.tal.blog.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tal.blog.entity.Post;


@Repository
public interface PostRepository extends JpaRepository<Post,Long>{

	@Query("SELECT DISTINCT p FROM Post p JOIN p.tags t WHERE p.title LIKE %?1% OR p.content LIKE %?1% OR p.excerpt LIKE %?1% OR p.author LIKE %?1%")
	public Page<Post> findByKeyword(String keyword,Pageable pageable);
	
	@Query("SELECT DISTINCT p FROM Post p JOIN p.tags t WHERE  p.author IN :listOfAuthor")
	public Page<Post> findAllByAuthor(@Param("listOfAuthor") String[] listOfAuthor ,Pageable pageable);
	
	@Query("SELECT DISTINCT p FROM Post p JOIN p.tags t WHERE  t.name IN :listOfTagName")
	public Page<Post> findAllByTagName(@Param("listOfTagName") String[] listOfAuthor ,Pageable pageable);

}
