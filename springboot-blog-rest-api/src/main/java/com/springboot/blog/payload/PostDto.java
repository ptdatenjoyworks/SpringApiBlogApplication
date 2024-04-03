package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Schema(
        description = "PostDto Model Information"
)
public class PostDto {
    private Long id;

    //title should not be null or empty
    //title should have at least 2 characters
    @Schema(
            description = "title of post"
    )
    @NotEmpty
    @Size(min = 2,message = "Post title should have at least 2 characters")
    private String title;

    //description should not be null or empty
    //description should have at least 10 characters
    @Schema(
            description = "Blog post description"
    )
    @NotEmpty
    @Size(min = 10,message = "Post description should have at least 10 characters")
    private String description;

    //post content should not be null or empty
    @Schema(
            description = "Blog post content"
    )
    @NotEmpty
    private String content;

    private Set<CommentDto> comments = new HashSet<>();

    @Schema(
            description = "Blog Post category"
    )
    @NotNull
    private Long categoryId;
}
