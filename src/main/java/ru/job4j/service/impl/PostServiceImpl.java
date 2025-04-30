package ru.job4j.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.entity.Post;
import ru.job4j.entity.User;
import ru.job4j.service.PostService;
import ru.job4j.repository.PostRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public boolean updatePost(Post post) {
        var optionalPost = postRepository.findById(post.getId());
        if (optionalPost.isEmpty()) {
            return false;
        }
        Post postToUpdate = optionalPost.get();
        postToUpdate.setContent(post.getContent());
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setImageUrl(post.getImageUrl());
        return true;
    }

    @Override
    public boolean deletePostById(Long id) {
        var optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            return false;
        }
        postRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void createNewPost(User author, String title, String content, String imageUrl) {
        var post = createPost(author, title, content, imageUrl);
        postRepository.save(post);
    }

    @Override
    public void updateTitleAndContent(String newTitle, String newContent, Post postToUpdate) {
        int rowsAffected = postRepository.updateTitleAndContent(newTitle, newContent, postToUpdate);
        if (rowsAffected == 0) {
            log.error("Post {} wasn't updated", postToUpdate.getId());
        }
    }

    @Override
    public void deletePost(Post postToDelete) {
        int rowsAffected = postRepository.deletePost(postToDelete);
        if (rowsAffected == 0) {
            log.error("Post {} wasn't deleted", postToDelete.getId());
        }
    }

    private Post createPost(User user, String title, String content, String imageUrl) {
        var post = new Post();
        post.setUser(user);
        post.setTitle(title);
        post.setContent(content);
        post.setImageUrl(imageUrl);
        post.setCreatedAt(OffsetDateTime.now());
        return post;
    }
}
