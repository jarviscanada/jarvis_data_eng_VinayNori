package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.Util.TweetUtil;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

    @Mock
    TwitterService service;

    @InjectMocks
    TwitterController controller;

    Double lon = 45d;
    Double lat = 45d;
    String text = "testing tweet";
    Tweet testTweet = TweetUtil.buildTweet(text, lon, lat);

    @Test
    public void postTweet() {
        String[] invalidLocArgs = {"post", "tweet text", "55"};
        String[] invalidLocArgs2 = {"post", "tweet text", "5S:4S"};
        String[] invalidTextArgs = {"post", "", "55:45"};
        String[] invalidLenArgs = {"post", "text", "45:45", "anotherArg"};
        String[] validArgs = {"post", "tweet text", "55:55"};

        when(service.postTweet(any())).thenReturn(testTweet);
        try {
            controller.postTweet(invalidLocArgs);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            controller.postTweet(invalidLenArgs);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            controller.postTweet(invalidLocArgs2);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            controller.postTweet(invalidTextArgs);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        Tweet tweet = controller.postTweet(validArgs);
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
        assertEquals(text, tweet.getText());
    }

    @Test
    public void showTweet() {
        String fields = "created_at,text,coordinates,entities";
        String[] validArgs = {"show", "1097607853932564480", fields};
        String[] invalidLenArgs = {"show"};
        String[] invalidFieldsArgs = {"show", "1097607853932564480", ""};

        when(service.showTweet(anyString(), any())).thenReturn(testTweet);

        try {
            controller.showTweet(invalidFieldsArgs);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            controller.showTweet(invalidLenArgs);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        Tweet tweet = controller.showTweet(validArgs);
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
        assertEquals(text, tweet.getText());
    }

    @Test
    public void deleteTweet() {
        String[] validArgs = {"delete", "1097607853932564480,1097607853932512345"};
        String[] invalidLenArgs = {"delete", "1097607853932512345", "another Arg"};
        String[] invalidIdArgs = {"delete", ""};
        List<Tweet> testTweets = new ArrayList<Tweet>() {
        };
        testTweets.add(testTweet);
        testTweets.add(testTweet);

        when(service.deleteTweets(any())).thenReturn(testTweets);
        try {
            controller.deleteTweet(invalidLenArgs);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            controller.deleteTweet(invalidIdArgs);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        List<Tweet> tweets = controller.deleteTweet(validArgs);
        tweets.stream().forEach(tweet -> {
            assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
            assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
            assertEquals(text, tweet.getText());
        });
    }
}