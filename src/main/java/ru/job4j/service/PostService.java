package ru.job4j.service;

import ru.job4j.entity.Post;
import ru.job4j.entity.User;

import java.util.Optional;

public interface PostService {

    Post savePost(Post post);

    void deletePostById(Long id);

    Optional<Post> getPostById(Long id);

    void createNewPost(User user, String title, String content, String imageUrl);

    void updateTitleAndContent(String newTitle, String newContent, Post postToUpdate);

    void deletePost(Post post);
}
