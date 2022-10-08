package com.tal.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Tags")
public class Tag {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long tagId;
 private String name;
 @CreationTimestamp
 private LocalDateTime createdAt;
 @UpdateTimestamp
 private LocalDateTime updatedAt;
 
 @ManyToMany(mappedBy="tags")
 @JsonIgnore
 private List<Post> posts= new ArrayList<>();


public Long getTagId() {
	return tagId;
}

public void setTagId(Long tagId) {
	this.tagId = tagId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public LocalDateTime getCreatedAt() {
	return createdAt;
}

public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
}

public LocalDateTime getUpdatedAt() {
	return updatedAt;
}

public void setUpdatedAt(LocalDateTime updatedAt) {
	this.updatedAt = updatedAt;
}

public List<Post> getPosts() {
	return posts;
}

public void setPosts(List<Post> posts) {
	this.posts = posts;
}

}
