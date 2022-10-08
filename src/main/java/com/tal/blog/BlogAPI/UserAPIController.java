package com.tal.blog.BlogAPI;

import com.tal.blog.entity.User;
import com.tal.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class UserAPIController {
    @Autowired
    UserService userService;

    @PostMapping("register")
    public String registerUserAccount(@RequestBody User user) {
        User responseUser = userService.save(user);
        if(responseUser.getFirstName().equals("exist"))
            return "user allready exist with this email/username";
        return "successfully register";
    }
}
