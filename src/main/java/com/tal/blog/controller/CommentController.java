package com.tal.blog.controller;


import com.tal.blog.service.UserService;
import com.tal.blog.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tal.blog.entity.Comment;
import com.tal.blog.entity.Post;
import com.tal.blog.service.CommentService;
import com.tal.blog.service.PostService;
@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;

	@PostMapping("/createComment/{postId}")
	public String createComment(@PathVariable("postId") Long postId,@ModelAttribute Comment comment) {
		Post post = new Post();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(!auth.equals("anonymous")) {
			String userEmail = auth.getName();
			String userName = userEmail.split("@")[0];
			comment.setName(userName);
			comment.setEmail(userEmail);
		}
		post=postService.getPostById(postId);
		comment.setPost(post);
		commentService.saveComment(comment);
		return "redirect:/fullContent/"+postId;
	}
	@GetMapping("/editComment/{commentId}")
	public String updateComment(@PathVariable("commentId") Long commentId,Model model) {
		Comment comment = new Comment();
		
		comment = commentService.getCommentById(commentId);
		commentService.deleteCommentById(commentId);
		model.addAttribute("comment",comment);
		return "editComments";
	}
	
	@GetMapping("/deleteComment/{commentId}")
	public String deleteComment(@PathVariable("commentId") Long commentId) {
		Comment comment=new Comment();
		comment = commentService.getCommentById(commentId);
		Long postId=comment.getPost().getPostId();
		commentService.deleteCommentById(commentId);
		return "redirect:/fullContent/"+postId;
	}

}
