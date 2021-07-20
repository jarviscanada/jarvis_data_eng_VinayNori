package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

    private CrdDao dao;

    @Autowired
    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    /**
     * Validate and post a user input Tweet
     *
     * @param tweet tweet to be created
     * @return created tweet
     * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long
     *                                  out of range
     */
    @Override
    public Tweet postTweet(Tweet tweet) {
        validatePostTweet(tweet);

        return (Tweet) dao.create(tweet);
    }

    private void validatePostTweet(Tweet tweet) {
        // text length > 140
        // lat and long range
        String text = tweet.getText();
        Double lng = tweet.getCoordinates().getCoordinates().get(0);
        Double lat = tweet.getCoordinates().getCoordinates().get(1);

        if (text.length() > 140) {
            throw new IllegalArgumentException("Text must be 140 characters or less.");
        } else if (lng > 90 || lng < -90) {
            throw new IllegalArgumentException(
                    "The longitude for the tweet must be in the range of [-90,90]");
        } else if (lat > 90 || lat < -90) {
            throw new IllegalArgumentException(
                    "The latitude for the tweet must be in the range of [-90,90]");
        }
    }

    /**
     * Search a tweet by ID
     *
     * @param id     tweet id
     * @param fields set fields not in the list to null
     * @return Tweet object which is returned by the Twitter API
     * @throws IllegalArgumentException if id or fields param is invalid
     */
    @Override
    public Tweet showTweet(String id, String[] fields) {
        validateId(id);

        return (Tweet) dao.findById(id);
    }


    /**
     * Delete Tweet(s) by id(s).
     *
     * @param ids tweet IDs which will be deleted
     * @return A list of Tweets
     * @throws IllegalArgumentException if one of the IDs is invalid.
     */
    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        Arrays.stream(ids).forEach(this::validateId);
        List<Tweet> tweets = new ArrayList<>();
        Arrays.stream(ids).forEach(id -> tweets.add((Tweet) dao.deleteById(id)));
        return tweets;
    }

    private void validateId(String id) {
        try {
            Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The id of the tweet is invalid");
        }
    }
}