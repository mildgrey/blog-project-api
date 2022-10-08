package com.tal.blog.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tal.blog.entity.Post;
import com.tal.blog.entity.Tag;
import com.tal.blog.repository.TagRepository;
import com.tal.blog.service.TagService;

@Service
public class TagServiceImpl implements TagService{
	
	@Override
	public Set<String> getAllUniqueTag(List<Tag> tags){
		   Set<String> allTag=new HashSet<>();
		   for(Tag tag:tags) {
				allTag.add(tag.getName());
			}
		   return allTag;
	   }
	
	
	@Autowired
	private TagRepository tagRepository;

	@Override
	public List<Tag> getTagsByName(String name) {
		return tagRepository.findTagByName(name);
	}

	@Override
	public void saveTag(Tag tag) {
		tagRepository.save(tag);
	}

	@Override
	public List<Tag> getAllTags() {
		
		return tagRepository.findAll();
	}	
}
