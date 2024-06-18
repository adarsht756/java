package Models;

/**
 * User
 */
public class User {

    private String userId;
    private String userName;
    private String role;

    public User(String userId, String userName, String role) {
        this.userId = userId;
        this.userName = userName;
        this.role = role;
    }

    public User(UserBuilder userBuilder) {
        this.userId = userBuilder.userId;
        this.userName = userBuilder.userName;
        this.role = userBuilder.role;
    }

    public String getuserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }

    public String getUserId() {
        return this.userId;
    }

    public static class UserBuilder {
        private String userId;
        private String userName;
        private String role;

        public UserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder setUserRole(String role) {
            this.role = role;
            return this;
        }

        public UserBuilder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}