import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
    }
    public String createPost(String userId, int postId, String content, String timestamp) {
        if (this.userService.hasUser(userId) == false) {
            return "User with id: " + userId + " do not exists, post create failed";
        }
        if (this.userPosts.containsKey(userId) == false)
            this.userPosts.put(userId, new HashSet<Post>());
        Post post = new Post(postId, userId, content, timestamp);
        this.userPosts.get(userId).add(post);
        this.feedService.addPostToUserFeed(userId, postId);
        notifyFollowers(userId, postId);
        return "User with id: " + userId + " have successfully created post";
    }

    public void notifyFollowers(String userId, Integer postId) {
        Set<String> followers = this.userService.getFollowersForUser(userId);
        for (String followerId: followers)
            this.feedService.addPostToUserFeed(followerId, postId);
    }

    public void deleteUserAllPosts(String userId) {
        this.userPosts.remove(userId);
    }

    public String deletePost(String userId, String postId) {
        if (this.userPosts.containsKey(userId) == false)
            return "User with Id: " + userId + " do not exists, delete post failed";
        if (this.userPosts.get(userId).contains(postId) == false)
            return "User with id: " + userId + " do not have post with id: " + postId;
        this.userPosts.get(userId).remove(postId);
        return "User with id: " + userId + " have successfully deleted post";
    }
}