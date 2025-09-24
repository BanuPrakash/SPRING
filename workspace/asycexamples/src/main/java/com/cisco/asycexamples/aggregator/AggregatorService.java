package com.cisco.asycexamples.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AggregatorService {
    @Autowired
    PostService postService;

    @Autowired
    UserService userService; // implementation classes are wired, using Proxy

    @Async("posts-pool")
    public CompletableFuture<List<PostDTO>> getPosts() {
        System.out.println(Thread.currentThread() + " getting posts");
        return  CompletableFuture.completedFuture(postService.getPosts());
    }

    @Async("users-pool")
    public CompletableFuture<List<UserDTO>> getUsers() {
        System.out.println(Thread.currentThread() + " getting users");
        return  CompletableFuture.completedFuture(userService.getUsers());
    }
}
