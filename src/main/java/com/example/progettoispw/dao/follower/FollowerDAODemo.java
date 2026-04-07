package com.example.progettoispw.dao.follower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowerDAODemo implements FollowerDAO {

    protected final Map<String, List<String>> followersMap = new HashMap<>();

    @Override
    public void follow(String buyerUsername, String sellerUsername) {
        followersMap.computeIfAbsent(sellerUsername, s -> new ArrayList<>()).add(buyerUsername);
    }

    @Override
    public void unfollow(String buyerUsername, String sellerUsername) {
        List<String> usernames = followersMap.get(sellerUsername);
        if(usernames != null) {
            usernames.remove(buyerUsername);
        }
    }

    @Override
    public List<String> getFollowers(String sellerUsername) {
        return followersMap.get(sellerUsername);
    }

    @Override
    public boolean isFollowing(String buyerUsername, String sellerUsername) {
        List<String> usernames = followersMap.get(sellerUsername);
        if(usernames == null) {
            return false;
        }
        return usernames.contains(buyerUsername);
    }
}
