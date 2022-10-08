package com.tal.blog.serviceImpl;

import java.util.List;

import com.tal.blog.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tal.blog.entity.Comment;
import com.tal.blog.repository.CommentRepository;
import com.tal.blog.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public List<Comment> getAllComments() {
		
		return commentRepository.findAll();
	}

	@Override
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
		
	}

	@Override
	public void deleteCommentById(Long commentId) {
		commentRepository.deleteById(commentId);
	}

	@Override
	public Comment getCommentById(Long commentId) {
		
		return commentRepository.findById(commentId).get();
	}

	@Override
	public String updateComment(Long commentId, Comment comment) {
		if (commentRepository.existsById(commentId)) {
			Comment commentFromDb = commentRepository.findById(commentId).get();
			commentFromDb.setTextComment(comment.getTextComment());
			commentRepository.save(commentFromDb);
			return "Comment updated Successfully";
		}
		return "Comment is not Present";
	}

}
