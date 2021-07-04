package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.CreateTweetUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    @Autowired
    public TwitterController(Service service) {
        this.service = service;
    }

    /**
     * Parse user argument and post a tweet by calling service classes
     *
     * @param args
     * @return a posted tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet postTweet(String[] args) {
        // "post" "my first Tweet" "43:79"
        if (args.length != 3) {
            throw new IllegalArgumentException("Invalid amount of arguments!");
        }

        String text = args[1];
        String coords = args[2];
        String[] cArr = coords.split(COORD_SEP);
        if (cArr.length != 2) {
            throw new IllegalArgumentException("Invalid amount of coordinates for the tweet!");
        }

        Double lng = null;
        Double lat = null;

        try {
            lng = Double.parseDouble(cArr[0]);
            lat = Double.parseDouble(cArr[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Unable to parse the coordinates to Double. Invalid input!");
        }
        Tweet tweet = CreateTweetUtil.createTweet(text, lng, lat);
        return service.postTweet(tweet);
    }

    /**
     * Parse user argument and search a tweet by calling service classes
     *
     * @param args
     * @return a tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet showTweet(String[] args) {
        // id of tweet, other fields;
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid amount of arguments!");
        }
        String[] fields = args.length > 2 ? args[2].split(COMMA) : null;
        return service.showTweet(args[1], fields);
    }

    /**
     * Parse user argument and delete tweets by calling service classes
     *
     * @param args
     * @return a list of deleted tweets
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public List<Tweet> deleteTweet(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid amount of arguments!");
        }
        String[] ids = args[1].split(COMMA);
        return service.deleteTweets(ids);
    }
}