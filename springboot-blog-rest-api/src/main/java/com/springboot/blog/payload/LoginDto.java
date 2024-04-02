package com.springboot.blog.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    public String userNameOrEmail;
    public String password;
}
