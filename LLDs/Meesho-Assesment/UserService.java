/**
 * UserService
 */
public interface UserService {

    public String createUser(String userID, String username, String email);
    public String deleteUser();
    public boolean follow(String followerId, String followeeId);
    public boolean unfollow(String followerId, String followeeId);
}