package Interfaces;
/**
 * PostService
 */
public interface PostService {
    public void createPost(String userId, int postId, String content, String timestamp);
    public void deletePost(String userId, int postId);
}