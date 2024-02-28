package com.myblog.myblog11.service.impl;

import com.myblog.myblog11.entity.Comment;
import com.myblog.myblog11.entity.Post;
import com.myblog.myblog11.exception.ResourceNotFoundException;
import com.myblog.myblog11.payload.CommentDto;
import com.myblog.myblog11.repository.CommentRepository;
import com.myblog.myblog11.repository.PostRepository;
import com.myblog.myblog11.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;

    private CommentRepository commentRepository;

    private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepository,CommentRepository commentRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper= modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, long posId) {
        Post post = postRepository.findById(posId).orElseThrow(
                () -> new ResourceNotFoundException(posId)
        );
        Comment comment = new Comment();
        comment.setEmail(commentDto.getEmail());
        comment.setText(commentDto.getText());
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        CommentDto dto =new CommentDto();
        dto.setId(savedComment.getId());
        dto.setEmail(savedComment.getEmail());
        dto.setText(savedComment.getText());

        return dto;
    }

    @Override
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDto updateComment(long posId, CommentDto commentDto) {
        Comment comment = commentRepository.findById(posId).orElseThrow(
                () -> new RuntimeException("Comment Not Found for id: " + posId)
        );

        Comment c = modelMapper.map(commentDto,Comment.class);
        c.setId(comment.getId());
        Comment savedComment = commentRepository.save(c);
        return modelMapper.map(savedComment,CommentDto.class);

    }

//    CommentDto mapToDto(Comment comment){
//        CommentDto dto = modelMapper.map(comment, CommentDto.class);
//        return  dto;
//    }
//    CommentDto mapToEntity(CommentDto commentDto){
//        CommentDto comment = modelMapper.map(commentDto, CommentDto.class);
//        return  comment;
//    }
}
