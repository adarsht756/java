import java.text.SimpleDateFormat;
import java.util.List;

import Model.Pair;
import Model.Post;
import Services.FeedService;
import Services.PostServiceImpl;
import Services.UserServiceImp;

/**
 * Facebook
 */
public class Facebook {
    UserServiceImp userService;
    PostServiceImpl postService;
    FeedService feedService;

    public String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }


    int postIds = 0;
    public Facebook() {
        this.userService = new UserServiceImp();
        this.feedService = new FeedService();
        this.userService.setPostServiceHelper(postService);
        this.postService = new PostServiceImpl(userService, feedService);
    }

    public void createUser(String userId, String username, String email) {
        this.userService.createUser(userId, username, email);
    }

    public void createPost(String userId, String content) {
        this.postService.createPost(userId, postIds++, content, getCurrentTimestamp());
    }

    public void follow(String followerId, String followeeId) {
        this.userService.follow(followerId, followeeId);
    }

    public void unfollow(String followerId, String followeeId) {
        this.userService.unfollow(followerId, followeeId);
    }

    public void deletePost(String userId, int postId) {
        this.postService.deletePost(userId, postId);
    }

    public void getUserFeed(String userId) {
        List<Pair> res = this.feedService.getUserFeed(userId);
        if (res == null) {
            System.out.println("User with id: " + userId + " have no posts in feed");
            return;
        }
        System.out.println("User with id: " + userId + " have following feed:");
        System.out.println("---------------------------");
        List<Post> posts = this.postService.getPostsWithPostIdAndUserID(res);
        for (Post post: posts) {
            System.out.println("User with id: " + post.userId + " posted content: " + post.content);
        }
    }

}