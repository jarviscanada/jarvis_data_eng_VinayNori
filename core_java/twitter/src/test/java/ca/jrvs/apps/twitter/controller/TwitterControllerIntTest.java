package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.Util.TweetUtil;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";
    private TwitterDao dao;
    private TwitterService service;
    private Controller controller;
    String hashTag = "#abc";
    String text = "@someone some random text " + hashTag;
    Double lon = 45d;
    Double lat = 45d;

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
        controller = new TwitterController(service);
    }

    @Test
    public void postTweet() {
        String[] args = {"post", text, "45:45"};
        Tweet tweet = controller.postTweet(args);

        assertNotNull(tweet.getCoordinates());
        assertEquals(text, tweet.getText());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));

    }

    @Test
    public void showTweet() {
        String fields = "created_at,text,coordinates,entities";
        String id = "1398786151872438273";
        String[] args = {"show", id, fields};
        Tweet tweet = controller.showTweet(args);
        System.out.println(tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(text, tweet.getText());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));

        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));

    }

    @Test
    public void deleteTweet() {
        String id = "1397968864634355722";
        String[] args = {"delete", id};
        List<Tweet> tweets = controller.deleteTweet(args);

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