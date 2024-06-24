package Services;
import java.util.HashMap;
import java.util.Set;

import Model.User;

/**
 * UserServiceImp
 */
public class UserServiceImp {
    HashMap<String, User> users;
    PostServiceImpl postServiceHelper;

    public UserServiceImp() {
        this.users = new HashMap<String, User>();
    }

    public void setPostServiceHelper(PostServiceImpl postServiceHelper) {
        this.postServiceHelper = postServiceHelper;
    }

    public boolean hasUser(String userId) {
        return this.users.containsKey(userId);
    }

    public String createUser(String userID, String username, String email) {
        if (users.containsKey(userID)) {
            return "UserId already exists, kindly choose another one";
        }
        User user = new User.UserBuilder().userId(userID).username(username).email(email).build();
        this.users.put(userID, user);
        return "User created successfully";
    }

    public String deleteUser(String userId) {
        if (users.containsKey(userId) == false)
            return "User with id: " + userId + " do not exists";
        this.users.remove(userId);
        this.postServiceHelper.deleteUserAllPosts(userId);
        return "User deleted successfully with id: " + userId;
    }

    public Set<String> getFollowersForUser(String userID) {
        return this.users.get(userID).getFollowers();
    }

    public boolean follow(String followerId, String followeeId) {
        if (users.containsKey(followerId) == false || users.containsKey(followeeId) == false) {
            System.out.println("Entered invalid followerId or followeeId");
            return false;
        }
        User user = this.users.get(followeeId);
        user.addFlollower(followerId);
        this.users.put(followeeId, user);
        System.out.println("Follower with ID: " + followerId + " have started following user with id: " + followeeId);
        return true;
    }

    public boolean unfollow(String followerId, String followeeId) {
        if (users.containsKey(followerId) == false || users.containsKey(followeeId) == false) {
            System.out.println("Entered invalid followerId or followeeId");
            return false;
        }
        User user = this.users.get(followeeId);
        if (user.hasFollower(followerId) == false) {
            System.out.println("User with id: " + followerId + " do not follows user with ID: " + followeeId);
            return false;
        }
        System.out.println("Follower with ID: " + followerId + " have unfollowed user with id: " + followeeId);
        return true;
    }
}