package Models;
/**
 * User
 */
public class User {

    private int id;
    private String username;
    private String role;

    public User(int id, String username, String role) {
        this.username = username;
        this.role = role;
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public String getRole() {
        return role;
    }
}