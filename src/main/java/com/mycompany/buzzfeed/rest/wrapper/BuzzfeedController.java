/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buzzfeed.rest.wrapper;

/**
 *
 * @author arelin
 */
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class BuzzfeedController {
    
    @RequestMapping("/api/timefeed")
    public String timefeed(
            @RequestParam(value="feed") String feed,
            @RequestParam(value = "startTime") Date startTime,
            @RequestParam(value = "endTime") Date startDate) {
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/api/keywords")
    public String keywords(
            @RequestBody List<String> keywords,
            @RequestParam(value="feed") String feed,
            @RequestParam(value = "startTime") Date startTime,
            @RequestParam(value = "endTime") Date startDate) {
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/api/threshold")
    public String threshold(
            @RequestParam(value="feed") String feed,
            @RequestParam(value = "startTime") Date startTime,
            @RequestParam(value = "threshold") int threshold,
            @RequestParam(value = "endTime") Date startDate) {
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/api/test")
    public String test() {
        return "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<body>\n" +
            "\n" +
            "<marquee>You're awesome and BuzzFeed is awesome</marquee>\n" +
            "<p>You passed the test!</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";
    }
}
