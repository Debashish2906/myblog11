package com.myblog.myblog11.service.impl;

import com.myblog.myblog11.entity.Post;
import com.myblog.myblog11.exception.ResourceNotFoundException;
import com.myblog.myblog11.payload.PostDto;
import com.myblog.myblog11.repository.PostRepository;
import com.myblog.myblog11.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(post.getDescription());
        post.setContent(postDto.getContent());
        Post savedPost = postRepository.save(post);

        PostDto dto = new PostDto();
        dto.setTitle(savedPost.getTitle());
        dto.setDescription(savedPost.getDescription());
        dto.setContent(savedPost.getContent());
        return dto;
    }

    @Override
    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort =  (sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePost=postRepository.findAll(pageable);
        List<Post> postList = pagePost.getContent();
        List<PostDto> postDtos=postList.stream()
                .map((post)->new PostDto(post.getId(),post.getTitle(),post.getDescription(),post.getContent()))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
       Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException(postId));
       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());
        postRepository.save(post);
        PostDto dto = new PostDto(post.getId(),post.getTitle(),post.getDescription(),post.getContent());
        return dto;
    }

    @Override
    public void  deletePost(Long posId) {
        Post post = postRepository.findById(posId).orElseThrow(() -> new ResourceNotFoundException(posId));
        postRepository.delete(post);

    }

    @Override
   public PostDto getPostById(long posId) {
        Post post = postRepository.findById(posId).orElseThrow(
                ()->new ResourceNotFoundException(posId)
        );

        PostDto dto = new PostDto();
        dto.setId((post.getId()));
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }

    @Override
    public PostDto getPostByTitle(String title) {
        Post byTitle = postRepository.findByTitle(title).orElseThrow(
                ()->new ResourceNotFoundException(6L)
        );
        PostDto dto = new PostDto();
        dto.setId(byTitle.getId());
        dto.setTitle(byTitle.getTitle());
        dto.setDescription(byTitle.getDescription());
        dto.setContent(byTitle.getContent());

        return dto;
    }
}
