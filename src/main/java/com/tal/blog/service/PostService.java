package com.tal.blog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.tal.blog.entity.Post;
import org.springframework.data.domain.Pageable;

public interface PostService {
	
	Set<String> getAllUniqueAuthor(List<Post> posts);
	
	List<Post> getAllPosts();
	
	void savePost(Post post);
	
	public Post getPostById(Long postId) ;
	
	String deletePostById(Long postId);
	
	Page<Post> searchPagination(Integer pageNo,int pageSize,String sortField,String sortDirection,String keyword);
	
	Page<Post> searchPagination(Integer pageNo,int pageSize,String sortField,String sortDirection,Map<String,String[]> columnAndFilteredColumn);

	String updatePost(Long postId, Post post);

	Page<Post> postPaginationAndSort(int offset,int pageSize,String field);
	Page<Post> searchPost(String query, Pageable pageable);
}
