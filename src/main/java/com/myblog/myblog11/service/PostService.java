package com.myblog.myblog11.service;

import com.myblog.myblog11.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    public PostDto  updatePost(PostDto postDto, Long postId);

    public  void deletePost( Long posId);


    PostDto getPostById(long posId);

    PostDto getPostByTitle(String title);
}
