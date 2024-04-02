package com.springboot.blog.payload;

import com.springboot.blog.entity.Post;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "Name of category should have at least 2 characters")
    private String name;
    @NotEmpty
    @Size(min = 10, message = "Description of category should have at least 10 characters")
    private String description;

    Set<Post> posts = new HashSet<>();
}
