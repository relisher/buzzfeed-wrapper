/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buzzfeed.rest.wrapper;

import java.util.Date;

/**
 *
 * @author arelin
 */
public class GetResults {
    
    
    
    public GetResults(Date startTime, Date endTime, String feed, int comments) {
        this(startTime, endTime, feed, "", comments);
    }
    
    public GetResults(Date startTime, Date endTime, String feed, String keyword) {
        this(startTime, endTime, feed, keyword, 0);
    }
    
    public GetResults(Date startTime, Date endTime, String feed) {
        this(startTime, endTime, feed, "", 0);        
    }
    
    public GetResults(Date startTime, Date endTime, String feed, String keyword, int comments) {
        
    }
    

    
    
}
