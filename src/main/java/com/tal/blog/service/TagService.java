package com.tal.blog.service;

import java.util.List;
import java.util.Set;

import com.tal.blog.entity.Tag;

public interface TagService {
	
	Set<String> getAllUniqueTag(List<Tag> tags);
	
	List<Tag> getTagsByName(String name);
	
	void saveTag(Tag tag);
	
	List<Tag>getAllTags();
}
