package com.myblog.myblog11.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long postId) {
        super("Post is not found with this id "+postId);
    }
}
