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
import org.json.JSONException;
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
        JSONObject response = new JSONObject().accumulate("success", 1); //setup response object
        ArrayList<JSONObject> buzzes = new ArrayList<>(); //collection for buzzes
        PostToBuzzfeed ptb = new PostToBuzzfeed(); //creating posting object
        JSONObject buzzfeedResponse; 
        try {
            buzzfeedResponse = new JSONObject(ptb.Buzzes(_feed, 1));
        }
        catch (IOException e) {
            return FAILURE.toString(); //no success if feed doesn't exist
        }
        
        JSONArray buzzArray = buzzfeedResponse.getJSONArray("buzzes");
        
        buzzIterator:
        for(int i = 0; i < buzzArray.length(); i++) { //iterate through all buzzes
           JSONObject buzz = buzzArray.getJSONObject(i); 
           Date parse;
           try {
                parse = BUZZDATE.parse(buzz.get("published_date").toString()); 
           }
           catch (ParseException e) {
                continue; 
           }
           if(parse.after(_startTime) && parse.before(_endTime)) { //compare buzzdates and passed dates
               if(_keywords) { //if keyword condition
                   String title = buzz.getString("title").toLowerCase();
                   String description = buzz.getString("description").toLowerCase();
                   for(String keyword : _wordList) {
                       keyword = keyword.toLowerCase();
                       if(!(title.contains(keyword) || description.contains(keyword))) { //check for keyword
                           continue buzzIterator;
                       }
                   }
                   buzzes.add(buzz);
               }
               else if(_comments) { //if threshold condition
                   
                   JSONObject comments;
                   try {
                       comments = new JSONObject(ptb.Comments(buzz.getString("id"), 1)); //post for comments
                   }
                   catch (IOException e) {
                       continue;
                   }
                   int numberComments = 0;
                   
                   JSONArray commentsArray = comments.getJSONArray("comments"); 
                   if(commentsArray != null) {
                       numberComments += commentsArray.length();
                        if(numberComments > _numberComments) {
                           buzzes.add(buzz);
                        }
                   }
                   
                   int next;
                   try {
                       next = comments.getJSONObject("paging").getInt("next");
                   }
                   catch (JSONException e) {
                       continue;
                   }
                   
                   while(next != 0) {
                        try {
                            comments = new JSONObject(ptb.Comments(buzz.getString("id"), next));
                        }
                        catch (IOException e) {
                            continue;
                        }
                        commentsArray = comments.getJSONArray("comments");
                        if(commentsArray != null) {
                            numberComments += commentsArray.length();
                        }
                        if(numberComments >= _numberComments) {
                           buzzes.add(buzz);
                           break;
                        }
                        try {
                            next = comments.getJSONObject("paging").getInt("next");
                        }
                        catch (JSONException e) {
                            break;
                        }
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
