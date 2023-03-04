package dev.be.sns.controller;

import dev.be.sns.controller.request.PostCreateRequest;
import dev.be.sns.model.Entity.response.Response;
import dev.be.sns.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication)
    {
        postService.create(request.getTitle(), request.getBody(), authentication.getName());
        return Response.success();
    }
}
