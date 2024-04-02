package com.springboot.blog.service;

import com.springboot.blog.entity.User;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.UserDto;

public interface AuthService {
    String login(LoginDto loginDto);

    LoginDto register(UserDto userDto);
}
