package Model;
import java.util.HashSet;
import java.util.Set;

/**
 * User
 */
public class User {
    public String userId;
    public String username;
    public String email;
    private Set<String> followers;

    public User(UserBuilder bulder) {
        this.userId = bulder.userId;
        this.username = bulder.username;
        this.email = bulder.email;
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

    public static class UserBuilder {
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

        public User build() {
            return new User(this);
        }
    }
}