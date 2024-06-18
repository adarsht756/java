import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User
 */
public class User {
    private String userId;
    private String username;
    private String email;
    private Set<String> followers;

    public User(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.followers = new HashSet<>();
    }
    public void addFlollower(String followerId) {
        this.followers.add(followerId);
    }

    public boolean hasFollower(String followerId) {
        return this.followers.contains(followerId);
    }

    public Set<String> getFollowers() {
        return this.followers;
    }
    public class UserBuilder {
        private String userId;
        private String username;
        private String email;


        public UserBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        // public UserBuilder() {
        //     this.User(this);
        // }
    }
}