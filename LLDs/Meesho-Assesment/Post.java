import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Post {
    private Integer postId;
    private String userId;
    private String content;
    private int likes;
    private HashMap<String, Set<String>> comments;
    private List<String> sharedBy;
    private String timestamp;

    public Post(int postId, String userId, String content, String timestamp) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.comments = new HashMap<>();
        this.sharedBy = new ArrayList<>();
        this.timestamp = timestamp;
    }

    public boolean addComment(String userId, String comment) {
        if (this.comments.containsKey(userId) == false)
            this.comments.put(userId, new HashSet<String>());
        this.comments.get(userId).add(comment);
        return true;
    }

    public boolean deleteComment(String userId, String comment) {
        if (this.comments.containsKey(userId) == false)
            return false;
        if (this.comments.get(userId).contains(userId) == false)
            return false;
        this.comments.get(userId).remove(comment);
        return true;
    }
}