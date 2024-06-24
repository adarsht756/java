/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        Facebook facebook = new Facebook();
        facebook.createUser("adarsh", "Adarsh Tiwar", "adars@sdf");
        facebook.createUser("sans", "Adarsh Tiwar", "adars@sdf");
        facebook.follow("adarsh", "sans");
        facebook.createPost("sans", "hello world");
        facebook.getUserFeed("sans");
        facebook.getUserFeed("adarsh");
        facebook.unfollow("adarsh", "sans");
        facebook.getUserFeed("adarsh");
    }
}