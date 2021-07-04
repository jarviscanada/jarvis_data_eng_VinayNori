import java.net.URI;

import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import org.junit.Before;
import org.junit.Test;

public class TwitterHttpHelperTest {

    TwitterHttpHelper tweet;

    @Before
    public void setUp() throws Exception {
        String cKey = System.getenv("cKey");
        String cSecret = System.getenv("cSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        tweet = new TwitterHttpHelper(cKey, cSecret, accessToken, tokenSecret);
    }

    @Test
    public void httpPost() throws Exception {
        tweet.httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status=TESTING3"));
    }

    @Test
    public void httpGet() throws Exception {
        tweet.httpGet(new URI("https://api.twitter.com/1.1/statuses/show.json?id=1374032223507116039"));
    }
}