package com.tal.blog.BlogAPI;
import com.tal.blog.entity.Post;
import com.tal.blog.entity.Tag;
import com.tal.blog.service.PostService;
import com.tal.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class PostAPIController {
    @Autowired
    PostService postService;
    @Autowired
    TagService tagService;
    @PostMapping("writepost")
    public void writePost(@RequestBody Post post){
        String content=post.getContent();
        post.setExcerpt(content.substring(0,content.length()/3));
        postService.savePost(post);
    }
    @GetMapping("readpost")
    public List<Post> readPost(){
        return postService.getAllPosts();
    }

    @DeleteMapping("deletepost/{postId}")
    public String deletePost(@PathVariable(value="postId") Long postId){
        return postService.deletePostById(postId);
    }

    @PutMapping("updatepost/{postId}")
    public String updatePost(@PathVariable(value = "postId") Long postId,@RequestBody Post post){
         return postService.updatePost(postId,post);
    }
    @GetMapping("pageandsort/{offset}/{pageSize}/{field}")
    public Page<Post> postPaginationAndSorting(@PathVariable int offset,@PathVariable int pageSize,@PathVariable String field){
        return postService.postPaginationAndSort(offset,pageSize,field);
    }
    @GetMapping("pageandsearch/{offset}/{pageSize}/{keyword}/{sortBy}")
    public Page<Post> postSearchingWithPage(@PathVariable Optional<Integer> offset,
                                            @PathVariable Optional<Integer> pageSize,
                                            @PathVariable Optional<String> keyword,
                                            @PathVariable Optional<String> sortBy){
        return postService.searchPost(keyword.orElse(""),PageRequest.of(offset.orElse(0),pageSize.orElse(4),Sort.Direction.ASC,sortBy.orElse("id")));
    }

}
