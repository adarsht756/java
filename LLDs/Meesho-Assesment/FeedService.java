import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * FeedService
 */
public class FeedService {

    HashMap<String, PriorityQueue<Integer>> userFeeds;

    public FeedService() {
        this.userFeeds = new HashMap<String, PriorityQueue<Integer>>();
    }

    public List<Integer> getUserFeed(String userId) {
        if (this.userFeeds.containsKey(userId) == false)
            return null;
        PriorityQueue<Integer> userFeed = this.userFeeds.get(userId);
        List<Integer> postIds = new ArrayList<>();
        while (userFeed.size() != 0) {
            postIds.add(userFeed.poll());
        }
        return postIds;
    }

    public boolean addPostToUserFeed(String userId, int postId) {
        if (this.userFeeds.containsKey(userId) == false)
        return false;
        PriorityQueue<Integer> userFeed = this.userFeeds.get(userId);
        if (userFeed.size() == 10)
            userFeed.remove();
        userFeed.add(postId);
        return true;
    }
}