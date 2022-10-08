package com.tal.blog.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tal.blog.entity.User;



public interface UserService extends UserDetailsService{
	User save(User user);

}