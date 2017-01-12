/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buzzfeed.rest.wrapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author arelin
 */
public class GetResults {
    
    private boolean _comments;
    private boolean _keywords;
    private List<String> _wordList;
    private int _numberComments;
    private Date _startTime;
    private Date _endTime;
    private String _feed;
    
    private final SimpleDateFormat BUZZDATE = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public final JSONObject FAILURE = new JSONObject().accumulate("success", 0);
    
    public GetResults(Date startTime, Date endTime, String feed, int comments) {
        this(startTime, endTime, feed, new ArrayList<String>(), comments);
    }
    
    public GetResults(Date startTime, Date endTime, String feed, List<String> keyword) {
        this(startTime, endTime, feed, keyword, 0);
    }
    
    public GetResults(Date startTime, Date endTime, String feed) {
        this(startTime, endTime, feed, new ArrayList<String>(), 0);        
    }
    
    private GetResults(Date startTime, Date endTime, 
            String feed, List<String> keyword, int comments) {
        _comments = (comments != 0);
        _keywords = (!keyword.isEmpty());
        _wordList = keyword;
        _numberComments = comments;
        _startTime = startTime;
        _endTime = endTime;
        _feed = feed;
    }

    public String query() {
        JSONObject response = new JSONObject().accumulate("success", 1);
        ArrayList<JSONObject> buzzes = new ArrayList<>();
        PostToBuzzfeed ptb = new PostToBuzzfeed();
        JSONObject buzzfeedResponse;
        try {
            buzzfeedResponse = new JSONObject(ptb.Buzzes(_feed));
        }
        catch (IOException e) {
            return FAILURE.toString();
        }
        JSONArray buzzArray = buzzfeedResponse.getJSONArray("buzzes");
        buzzIterator:
        for(int i = 0; i < buzzArray.length(); i++) {
           JSONObject buzz = buzzArray.getJSONObject(i);
           Date parse;
           try {
                parse = BUZZDATE.parse(buzz.get("published_date").toString());
           }
           catch (ParseException e) {
                continue; 
           }
           if(parse.after(_startTime) && parse.before(_endTime)) {
               if(_keywords) {
                   String title = buzz.getString("title").toLowerCase();
                   String description = buzz.getString("description").toLowerCase();
                   for(String keyword : _wordList) {
                       keyword = keyword.toLowerCase();
                       if(!(title.contains(keyword) || description.contains(keyword))) {
                           continue buzzIterator;
                       }
                   }
                   buzzes.add(buzz);
               }
               else if(_comments) {
                   JSONObject comments;
                   try {
                       comments = new JSONObject(ptb.Comments(buzz.getString("id")));
                   }
                   catch (IOException e) {
                       continue;
                   }
                   JSONArray commentsArray = comments.getJSONArray("comments");
                   if(commentsArray != null && 
                           _numberComments <= comments.getJSONArray("comments").length()){
                       buzzes.add(buzz);
                   }
               }
               else {
                   buzzes.add(buzz); 
               }
           }
           else if(parse.before(_startTime)) {
               break;
           }
        }
        response.accumulate("buzzes", buzzes);
        return response.toString();
    }
}
