package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TwitterJsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TwitterCLIApp {

    private Controller controller;

    @Autowired
    public TwitterCLIApp(Controller controller) {
        this.controller = controller;
    }

    public void run(String[] args) {

        String req = args[0].toLowerCase();
        if (req.equals("get")) {
            printTweet(controller.showTweet(args));
        } else if (req.equals("post")) {
            printTweet(controller.postTweet(args));
        } else if (req.equals("delete")) {
            List<Tweet> tweets = controller.deleteTweet(args);
            tweets.stream().forEach(this::printTweet);
        } else {
            throw new IllegalArgumentException("TwitterAppCLI post/get/delete [options]");
        }
    }

    private void printTweet(Tweet tweet) {
        try {
            System.out.println(TwitterJsonParser.toJson(tweet, true, true));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to parse object to Json!");
        }

    }
}