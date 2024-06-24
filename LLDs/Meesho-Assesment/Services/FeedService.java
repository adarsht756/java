package Services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import Model.Pair;

/**
 * FeedService
 */
public class FeedService {

    HashMap<String, PriorityQueue<Pair>> userFeeds;

    public FeedService() {
        this.userFeeds = new HashMap<String, PriorityQueue<Pair>>();
    }

    public List<Pair> getUserFeed(String userId) {
        if (this.userFeeds.containsKey(userId) == false)
            return null;
        PriorityQueue<Pair> userFeed = this.userFeeds.get(userId);
        System.out.println(userFeed);
        List<Pair> postIds = new ArrayList<>();
        while (userFeed.size() != 0) {
            postIds.add(userFeed.poll());
        }
        return postIds;
    }

    public boolean addPostToUserFeed(String userId, String userIdWhoCreatedPost, int postId) {
        if (this.userFeeds.containsKey(userId) == false)
            this.userFeeds.put(userId, new PriorityQueue<>());
        PriorityQueue<Pair> userFeed = this.userFeeds.get(userId);
        if (userFeed.size() == 10)
            userFeed.remove();
        userFeed.add(new Pair(postId, userIdWhoCreatedPost));
        // System.out.println("userFeed " + userFeed);
        return true;
    }
}