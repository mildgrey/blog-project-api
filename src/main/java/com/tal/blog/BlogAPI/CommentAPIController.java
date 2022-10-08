package com.tal.blog.BlogAPI;

import com.tal.blog.entity.Comment;
import com.tal.blog.entity.Post;
import com.tal.blog.service.CommentService;
import com.tal.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentAPIController {
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    @PostMapping("writecomment/{postId}")
    public Comment writeComment(@PathVariable("postId") Long postId, @RequestBody Comment comment) {
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
        return comment;
    }

    @PutMapping("updatecomment/{commentId}")
    public String updateComment(@PathVariable("commentId") Long commentId,@RequestBody Comment comment) {
        return commentService.updateComment(commentId,comment);
    }
}
