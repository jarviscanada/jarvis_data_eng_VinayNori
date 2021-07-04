package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.Util.TweetUtil;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService service;

    Double lon = 45d;
    Double lat = 45d;
    String text = "testing tweet";
    Tweet testTweet = TweetUtil.buildTweet(text, lon, lat);

    @Test
    public void postTweet() {
        String invalidText = "This is a string that will be used to throw an exception when"
                + " a user tries to use more than 140 characters in their tweet. So, I have added "
                + "this last sentence to make sure the number of characters is more than one "
                + "hundred and forty";
        Tweet invalidTextTweet = TweetUtil.buildTweet(invalidText, 45, 45);
        Tweet invalidLonTweet = TweetUtil.buildTweet(text, 181, 30);
        Tweet invalidLatTweet = TweetUtil.buildTweet(text, 100, -91);
        Tweet validTweet = TweetUtil.buildTweet(text, 10, 10);

        when(dao.create(any())).thenReturn(testTweet);
        try {
            service.postTweet(invalidTextTweet);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            service.postTweet(invalidLonTweet);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            service.postTweet(invalidLatTweet);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        Tweet tweet = service.postTweet(validTweet);
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
        assertEquals(text, tweet.getText());
    }

    @Test
    public void showTweet() {
        String validId = "1097607853932564480";
        String invalidId = "1097607853932564abc";
        String[] emptyFields = new String[]{};
        String[] invalidFields = {"created_at", "Sai", "Prateek"};
        String[] validFields = {"created_at", "text", "coordinates", "entities"};
        when(dao.findById(any())).thenReturn(testTweet);
        try {
            service.showTweet(validId, invalidFields);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            service.showTweet(invalidId, emptyFields);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            service.showTweet(invalidId, validFields);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        Tweet tweet = service.showTweet(validId, validFields);
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
        assertEquals(text, tweet.getText());
    }

    @Test
    public void deleteTweets() {
        String[] validIds = {"1097607853932564480", "1097607853932512345"};
        String[] invalidIds = {"1097607853932564abc", "1097607853932564480"};
        when(dao.deleteById(any())).thenReturn(testTweet);
        try {
            service.deleteTweets(invalidIds);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        List<Tweet> tweets = service.deleteTweets(validIds);
        tweets.stream().forEach(tweet -> {
            assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
            assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
            assertEquals(text, tweet.getText());
        });
    }
}