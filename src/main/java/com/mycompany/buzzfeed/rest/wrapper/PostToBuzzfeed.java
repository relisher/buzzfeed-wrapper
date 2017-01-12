/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buzzfeed.rest.wrapper;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author arelin
 */


public class PostToBuzzfeed {
    
    private final String FEED_SEARCH =  "http://www.buzzfeed.com/api/v2/feeds/";
    private final String COMMENT_SEARCH = "http://www.buzzfeed.com/api/v2/comments/";
    
    OkHttpClient client = new OkHttpClient();

    public String Buzzes(String feed) throws IOException {
        Request request = new Request.Builder()
            .url(FEED_SEARCH + feed)
            .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    
    public String Comments(String buzz) throws IOException {
        Request request = new Request.Builder()
            .url(COMMENT_SEARCH + buzz)
            .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    
}
