package com.myblog.myblog11.controller;

import com.myblog.myblog11.payload.CommentDto;
import com.myblog.myblog11.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {


    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8081/api/comments?postId=1
    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @RequestParam long postId
    ){
        CommentDto dto = commentService.createComment(commentDto, postId);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8081/api/comments/2
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable long id){
        commentService.deleteComment(id);
        return new ResponseEntity<>("Comment is deleted!!",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<CommentDto> updateComment(@PathVariable long id, @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.updateComment(id, commentDto);
        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
