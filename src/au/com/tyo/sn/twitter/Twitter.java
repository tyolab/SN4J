/*
 * Copyright (C) 2014 TYONLINE TECHNOLOGY PTY. LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package au.com.tyo.sn.twitter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import au.com.tyo.sn.SecretTwitter;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Twitter {
	
	public static final String REQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token"; 
	public static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
	public static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
	
	private twitter4j.Twitter twitter;
	
	protected SecretTwitter secret;
	
	public Twitter() {
		secret = null;
	}
	
	public void authenticate(String consumerKey, String consumerKeySecret) {
		if (secret == null || secret.isBlank()) {
			getAppAuthorized();
			return;
		}
		
		AccessToken accessToken = new AccessToken(secret.getToken(), secret.getSecret());
		
        Configuration conf = new ConfigurationBuilder()
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerKeySecret)
                .setOAuthAccessToken(accessToken.getToken())
                .setOAuthAccessTokenSecret(accessToken.getTokenSecret())
                .build();

        OAuthAuthorization auth = new OAuthAuthorization(conf);
        twitter = new TwitterFactory().getInstance(auth); 
	}

	public void getAppAuthorized() {
		
	}

	public Status postTweet(String tweet) throws TwitterException {
		return twitter.updateStatus(tweet);
	}
	
	public Status postTweet(String tweet, String url) throws TwitterException {
		return twitter.updateStatus(tweet);
	}
	
	public Status postTweetWithImage(String imgTitle, String imgUrl, String message) throws TwitterException  {
        StatusUpdate what = new StatusUpdate(message);
        
        try {
	        URL url = new URL(imgUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        InputStream is = url.openStream();
	        what.setMedia(imgTitle, is);
        }
        catch (Exception ex) {
        	/*
        	 * Something wrong, but that is ok, just ignore it
        	 */
        }
  
        return twitter.updateStatus(what);
	}
	
	public String getAuthorizationUrl() {
		String url = null;
		return url;
	}
	
	public boolean updateStatus(String status, String mediaUrl) {
		return false;
	}

}
