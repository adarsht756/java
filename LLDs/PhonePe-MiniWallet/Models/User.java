package Models;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String userId;
    private String username;
    private String phoneNumber;
    private Set<String> offerIDsUsed;

    public User(UserBuilder userBuilder) {
        this.userId = userBuilder.userId;
        this.username = userBuilder.username;
        this.phoneNumber = userBuilder.phoneNumber;
        this.offerIDsUsed = new HashSet<String>();
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPhone() {
        return this.phoneNumber;
    }

    public Set<String> getOfferIdsUsed() {
        return this.offerIDsUsed;
    }

    public void addUsedOfferID(String offerID) {
        this.offerIDsUsed.add(offerID);
    }

    public static class UserBuilder {
        private String userId;
        private String username;
        private String phoneNumber;

        public UserBuilder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}