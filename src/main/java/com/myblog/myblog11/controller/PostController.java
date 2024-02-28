package com.myblog.myblog11.controller;

import com.myblog.myblog11.payload.ExceptionResponse;
import com.myblog.myblog11.payload.PostDto;
import com.myblog.myblog11.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    @Autowired
    private PostService postService;
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //http:localhost:8081/api/posts?pageNo=0&pageSize=3&sortBy=title&sortDir=desc
    @GetMapping
    public ResponseEntity<List<PostDto>> getPost(
            @RequestParam(name = "pageNo", required = false , defaultValue = "0") int pageNo,
             @RequestParam(name = "pageSize", required = false , defaultValue = "3") int pageSize,
            @RequestParam(name = "SortBy", required = false , defaultValue = "id") String sortBy,
            @RequestParam(name = "SortDir", required = false , defaultValue = "id") String sortDir
    ){
        return new ResponseEntity<>(postService.getAllPost(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    @PutMapping("/{postId}")
    public  ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Long postId){
        PostDto dto = postService.updatePost(postDto,postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @DeleteMapping("/{postId}")
    public  ResponseEntity<ExceptionResponse> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return  new ResponseEntity<>(new ExceptionResponse(1,"Post deleted succesfully"),HttpStatus.OK);
    }
    //http:localhost:8081/api/posts/id=1
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long postId){
        PostDto dto = postService.getPostById(postId);
        return new ResponseEntity<>(dto,HttpStatus.OK);

    }
    @GetMapping("/{byTitle}")
    public  ResponseEntity<PostDto>  getPostByTitle(@RequestBody PostDto postDto){
        PostDto dto = postService.getPostByTitle(postDto.getTitle());
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
