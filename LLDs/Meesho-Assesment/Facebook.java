/**
 * Facebook
 */
public class Facebook {
    UserServiceImp userService;
    PostServiceImpl postService;
    FeedService feedService;

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
        this.postService.createPost(userId, postIds++, content, "null");
    }

    public void follow(String followerId, String followeeId) {
        // this.userService
    }

    public void unfollow(String followerId, String followeeId) {

    }

    public void createPost() {

    }

    public void deletePost() {

    }

}