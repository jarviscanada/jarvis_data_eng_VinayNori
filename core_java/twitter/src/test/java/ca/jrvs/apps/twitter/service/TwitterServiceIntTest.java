package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.Util.JsonUtil;
import ca.jrvs.apps.twitter.Util.TweetUtil;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest {

    private TwitterDao dao;
    private TwitterService service;
    String hashTag = "#abc";
    String text = "@someone some random text " + hashTag;
    Double lat = 45d;
    Double lon = -45d;

    @Before
    public void setUp() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken,
                tokenSecret);

        dao = new TwitterDao(httpHelper);
        service = new TwitterService(dao);
    }

    @Test
    public void postTweet() {
        Tweet postTweet = TweetUtil.buildTweet(text, lon, lat);
        Tweet tweet = service.postTweet(postTweet);

        assertNotNull(tweet.getCoordinates());
        assertEquals(text, tweet.getText());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
    }

    @Test
    public void showTweet() throws JsonProcessingException {
        String id = "1397968864634355722";
        String[] fields = {"created_at", "text", "coordinates", "entities"};
        Tweet tweet = service.showTweet(id, fields);

        assertNotNull(tweet.getCoordinates());
        assertEquals(text, tweet.getText());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
    }

    @Test
    public void deleteTweets() {
        String[] ids = {"1397968864634355722"};
        List<Tweet> tweets = service.deleteTweets(ids);
        assertTrue(tweets.size() == 1);

        Tweet tweet = tweets.get(0);
        assertNotNull(tweet.getCoordinates());
        assertEquals(text, tweet.getText());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
    }
}