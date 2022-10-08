package com.tal.blog.controller;


import com.tal.blog.entity.Post;
import com.tal.blog.entity.Tag;
import com.tal.blog.service.PostService;
import com.tal.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/fullContent/{postId}")
	public String showFullContent(@PathVariable(value="postId") Long postId,Model model) {
		Post post = new Post();
		post=postService.getPostById(postId);
		model.addAttribute("post",post);
		return "fullBlogContent";
	}
	
	@GetMapping("/createNewPost")
	public String createNewPost(Model model) {
		Post post = new Post();
		post.setPublishedAt("bengaluru");
		model.addAttribute("post",post);
		return "createPost";
	}
	@PostMapping("/savePost")
	public String savePost(@ModelAttribute("post") Post post) {
		String content;
		String tagsStr[]=post.getTags().get(0).getName().replaceAll("\\s", "").split("#");
		
		List<Tag>tagsList=new ArrayList<>();
		int lengthOfTagsStr=tagsStr.length;

		for(int i = 0;i<lengthOfTagsStr;i++) {
				Tag newTag=new Tag();
				newTag.setName(tagsStr[i].toLowerCase());
	            if(tagService.getTagsByName(tagsStr[i]).size()==0 && tagsStr[i]!=null && !tagsStr[i].equals("")) {
	                tagService.saveTag(newTag);
	            }
	            if(tagsStr[i]!=null && !tagsStr[i].equals(""))
	            tagsList.add(newTag);
		}
		post.setTags(tagsList);
		content=post.getContent();
		post.setExcerpt(content.substring(0,content.length()/3));
		postService.savePost(post);
		return "redirect:/";
	}
	@GetMapping("/updatePost/{postId}")
	public String updatePost(@PathVariable(value="postId") Long postId,Model model) {
		
		Post post =postService.getPostById(postId);
		model.addAttribute("post",post);
		return "updatePost";
	}
	@GetMapping("/deletePost/{postId}")
	public String deletePost(@PathVariable(value="postId") Long postId)  {
		postService.deletePostById(postId);
		return "redirect:/";
	}

	@GetMapping({"/page/{currentPage}","/page/","/"})
	public String allBlogDisplayAsPage(@PathVariable(value="currentPage",required=false) Integer pageNo,
			@RequestParam(value="sortField",defaultValue="createdAt",required=false) String sortField,
			@RequestParam(value="sortDir",defaultValue="asc",required=false) String sortDir,
			@RequestParam(value="keyword",defaultValue="",required=false) String keyword,
			@RequestParam(value="author",defaultValue="",required=false) String filteredAuthor,
			@RequestParam(value="tagName",defaultValue="",required=false) String filteredTagName,
			Model model) {
		int pageSize=4;
		
		if(pageNo==null)
			pageNo=1;
		
		Page<Post> page;

		List<Post> posts= postService.getAllPosts();
		List<Tag> tags= tagService.getAllTags();
		
		Set<String >allAuthor=postService.getAllUniqueAuthor(posts);
		Set<String >allTagName=tagService.getAllUniqueTag(tags);
		Map<String,String[]> columnAndFilteredColumn=new HashMap<>(); 
		
		String listOfAuthor[]=filteredAuthor.split(",");
		String listOfTagName[]=filteredTagName.split(",");
		
		columnAndFilteredColumn.put("author",listOfAuthor);
		columnAndFilteredColumn.put("tagName",listOfTagName);
		
		if(!listOfAuthor[0].equals("") || !listOfTagName[0].equals("") ) {
			page=postService.searchPagination(pageNo, pageSize,sortField,sortDir,columnAndFilteredColumn);
			if(!listOfAuthor[0].equals("")) {
			   model.addAttribute("allAuthor",filteredAuthor);
			   }
			else {
				model.addAttribute("allTagName",filteredTagName);
			}	
		}
		else {
			page=postService.searchPagination(pageNo, pageSize,sortField,sortDir,keyword);
			model.addAttribute("author","");
		}
		
		if(keyword==null)
			model.addAttribute("keyword", "");
		else
			model.addAttribute("keyword", keyword);
		
		model.addAttribute("allAuthor",allAuthor);
		model.addAttribute("allTagName",allTagName);
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDir);
		model.addAttribute("reverseSortDir",sortDir.equalsIgnoreCase("asc")? "desc":"asc");
		model.addAttribute("listPosts",page.getContent());
		return  "home";	
	}
}
