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

import android.content.res.Resources.NotFoundException;
import au.com.tyo.sn.SNBase;
import au.com.tyo.sn.SecretTwitter;
import au.com.tyo.sn.Secrets;
import au.com.tyo.sn.SocialNetworkConstants;
import au.com.tyo.sn.android.Callback;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class SNTwitter extends SNBase {
	
	public static final String REQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token"; 
	public static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
	public static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
	
	private Twitter twitter;
	
	protected SecretTwitter secretId;
	
	protected SecretTwitter secretToken;
	
	private boolean authenticated;
	
	private Callback callback;
	
	public SNTwitter() {
		this.authenticated = false;
	}
	
	public synchronized SecretTwitter getSecretId() {
		return secretId;
	}

	public synchronized void setSecretId(SecretTwitter secretId) {
		this.secretId = secretId;
	}

	public synchronized SecretTwitter getSecretToken() {
		return secretToken;
	}

	public synchronized void setSecretToken(SecretTwitter secretToken) {
		this.secretToken = secretToken;
	}

	public Callback getCallback() {
		return callback;
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public void authenticate(String consumerKey, String consumerKeySecret) throws TwitterException {
		if (secretToken == null || secretToken.isBlank()) {
			getAppAuthorized(consumerKey, consumerKeySecret);
			return;
		}
		
		AccessToken accessToken = new AccessToken(secretToken.getToken(), secretToken.getSecret());
		
        Configuration conf = new ConfigurationBuilder()
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerKeySecret)
                .setOAuthAccessToken(accessToken.getToken())
                .setOAuthAccessTokenSecret(accessToken.getTokenSecret())
                .build();

        OAuthAuthorization auth = new OAuthAuthorization(conf);
        twitter = new TwitterFactory().getInstance(auth); 
        authenticated = true;
	}

	public void getAppAuthorized(String consumerKey, String consumerKeySecret) throws TwitterException {
		Twitter twitter = getTwitter();
	    twitter.setOAuthConsumer(consumerKey, consumerKeySecret);
	    RequestToken requestToken = twitter.getOAuthRequestToken();
	    
	    openAuthorizationURL(requestToken.getAuthorizationURL());
	}

	private Twitter getTwitter() {
		if (twitter == null)
			twitter = new TwitterFactory().getInstance();
		return twitter;
	}

	protected void openAuthorizationURL(String authorizationURL) {
		throw new UnsupportedOperationException("Override and implement this method (openAuthorizationURL()) are needed");
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
	
	public boolean updateStatus(String status, String mediaUrl) {
		return false;
	}

	public void authenticate() throws NotFoundException, TwitterException {
		throw new UnsupportedOperationException("Override and implement this method (authenticate()) are needed");
	}

	public boolean isAuthenticated() {
		return this.authenticated;
	}

	public void processAccessToken(String token, String verifier) throws TwitterException {
        Twitter t = this.getTwitter();

        AccessToken accessToken = t.getOAuthAccessToken(token, verifier);
        
        secretId = new SecretTwitter(SocialNetworkConstants.AUTHENTICATION_OAUTH_ID);
        secretId.setToken(String.valueOf(accessToken.getUserId()));
        
        secretToken = new SecretTwitter(SocialNetworkConstants.AUTHENTICATION_OAUTH_ACCESS_TOKEN);
        secretToken.setToken(accessToken.getToken());
        secretToken.setSecret(accessToken.getTokenSecret());

		secrets.save(secretId);
	}

	@Override
	public void loadSecretsFromSafe() {
		if (secrets != null) {
			secretToken = (SecretTwitter) secrets.get(SocialNetworkConstants.TWITTER, SocialNetworkConstants.AUTHENTICATION_OAUTH_ACCESS_TOKEN);
			secretId = (SecretTwitter) secrets.get(SocialNetworkConstants.TWITTER, SocialNetworkConstants.AUTHENTICATION_OAUTH_ID);
		}
	}

	@Override
	public void saveSecretsToSafe() {
		if (secretToken != null && secretId != null) {
			secrets.save(secretToken);
			secrets.save(secretId);
		}
	}
	

}
