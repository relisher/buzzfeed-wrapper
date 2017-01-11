/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buzzfeed.rest.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    
    public GetResults(Date startTime, Date endTime, 
            String feed, List<String> keyword, int comments) {
        _comments = (comments != 0);
        _keywords = (!keyword.isEmpty());
        _wordList = keyword;
        _numberComments = comments;
        _startTime = startTime;
        _endTime = endTime;
        _feed = feed;
    }
    
    
    
}
