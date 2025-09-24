package com.cisco.asycexamples.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/posts")
public class PostController {
    @Autowired
    AggregatorService service;

    record PostUserDTO(String title, String email) {}

    @GetMapping
    public List<PostUserDTO> getPosts() {
        CompletableFuture<List<PostDTO>> posts = service.getPosts();  // posts is a placeholder where thread is going to dump result
        CompletableFuture<List<UserDTO>> users = service.getUsers();
        // above both runs concurrently

        // join makes the caller thread to wait for other thread to finish
        // barrier
        List<PostDTO> postList = posts.join(); // blocked
        List<UserDTO> usersList = users.join();

        return postList.stream().map(post -> {
            String email = usersList.stream()
                    .filter(user -> user.id() == post.userId())
                    .findFirst().get().email();
            return new PostUserDTO(post.title(), email);
        }).collect(Collectors.toList());
    }
}
