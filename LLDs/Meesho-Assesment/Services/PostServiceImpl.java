package Services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Model.Pair;
import Model.Post;

/**
 * PostServiceImpl
 */
public class PostServiceImpl {
    HashMap<String, Set<Post>> userPosts;
    UserServiceImp userService;
    FeedService feedService;

    public PostServiceImpl(UserServiceImp userService, FeedService feedService) {
        this.userService = userService;
        this.feedService = feedService;
        this.userPosts = new HashMap<>();
    }

    public void createPost(String userId, int postId, String content, String timestamp) {
        if (this.userService.hasUser(userId) == false) {
            System.out.println("User with id: " + userId + " do not exists, post create failed");
            return;
        }
        if (this.userPosts.containsKey(userId) == false)
            this.userPosts.put(userId, new HashSet<Post>());
        Post post = new Post(postId, userId, content, timestamp);
        this.userPosts.get(userId).add(post);
        this.feedService.addPostToUserFeed(userId, userId, postId);
        notifyFollowers(userId, postId);
        System.out.println("User with id: " + userId + " have successfully created post at time: " + timestamp);
    }

    public void notifyFollowers(String userId, Integer postId) {
        Set<String> followers = this.userService.getFollowersForUser(userId);
        for (String followerId: followers)
            this.feedService.addPostToUserFeed(followerId, userId, postId);
    }

    public void deleteUserAllPosts(String userId) {
        this.userPosts.remove(userId);
    }

    public String deletePost(String userId, int postId) {
        if (this.userPosts.containsKey(userId) == false)
            return "User with Id: " + userId + " do not exists, delete post failed";
        Set<Post> userPosts = this.userPosts.get(userId);
        Post toBeDeleted = null;
        for (Post post: userPosts) {
            if (post.postId.equals(postId)) {
                toBeDeleted = post;
                break;
            }
        }
        if (toBeDeleted == null)
            return "User with id: " + userId + " do not have post with id: " + postId;
        this.userPosts.get(userId).remove(toBeDeleted);
        return "User with id: " + userId + " have successfully deleted post";
    }

    public List<Post> getPostsWithPostIdAndUserID(List<Pair> a) {
        List<Post> posts = new ArrayList<>();
        for (Pair i: a) {
            Set<Post> post = this.userPosts.get(i.getSecond());
            for (Post p: post) {
                if (p.postId == i.getFirst()) {
                    posts.add(p);
                    break;
                }
            }
        }
        return posts;
    }
}