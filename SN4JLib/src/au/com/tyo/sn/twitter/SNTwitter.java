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

import android.net.Uri;
import au.com.tyo.sn.Message;
import au.com.tyo.sn.SNBase;
import au.com.tyo.sn.SecretOAuth;
import au.com.tyo.sn.SocialNetwork;
import au.com.tyo.sn.UserInfo;
import twitter4j.Relationship;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
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

	private RequestToken requestToken;
	
	private User user;
	
	public SNTwitter() {
		super(SocialNetwork.TWITTER);
		
		secretOAuth = new SecretOAuth(SocialNetwork.TWITTER);
		userInfo = new UserInfo(SocialNetwork.TWITTER, SocialNetwork.INFORMATION_USER_PROFILE);
		alias = new UserInfo(SocialNetwork.TWITTER, SocialNetwork.INFORMATION_USER_ALIAS);
		
		requestToken = null;
		
		user = null;
		twitter = null;
	}

	public synchronized SecretOAuth getSecretToken() {
		return secretOAuth;
	}

	public synchronized void setSecretToken(SecretOAuth secretToken) {
		this.secretOAuth = secretToken;
	}
	
	public void createInstance() {
		synchronized(this) {
			try {	
				if (hasSecret()) {
					AccessToken accessToken = new AccessToken(secretOAuth.getToken().getToken(), 
							secretOAuth.getToken().getSecret());
					
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
			}
			catch (Exception ex) {
				authenticated = false;
				twitter = null;
			}
			
			try {
		        /*
		         * it is better to use the id, because people would change their name
		         */
				long sourceId = Integer.valueOf(secretOAuth.getId().getToken());
				user = twitter.showUser(sourceId);
//		        user = twitter.showUser(userInfo.getName());
				
				secretOAuth.getId().setToken(String.valueOf(user.getId()));
				secrets.save(secretOAuth.getId());
				
				userInfo.setName(user.getScreenName());
				alias.setName(user.getName());
				
				saveUserInfo();
				saveAlias();
				userProfileImageUrl = getUserAvatarUrl();
			}
			catch (Exception ex) {
			}
		}
	}

	public void authenticate(String consumerKey, String consumerKeySecret) throws TwitterException {
		if (secretOAuth.isBlank()) {
			getAppAuthorized(consumerKey, consumerKeySecret);
			return;
		}
		
		createInstance();
	}

	public void getAppAuthorized(String consumerKey, String consumerKeySecret) throws TwitterException {
		twitter = getTwitter();
		try {
		    twitter.setOAuthConsumer(consumerKey, consumerKeySecret);
		}
		catch (Exception ex) {
			// the consumerkey and consumerKeySecret may have been already set 
		}
	    requestToken = twitter.getOAuthRequestToken(getCallback().toString());
	    
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

	public void authenticate() throws Exception {
		throw new UnsupportedOperationException("Override and implement this method (authenticate()) are needed");
	}

	public boolean isAuthenticated() {
		return this.authenticated;
	}

	@Override
	public void retrieveAccessToken(Uri uri) throws TwitterException {
        Twitter t = this.getTwitter();
        
        assert(requestToken != null);
        
        String token = uri.getQueryParameter("oauth_token");
        String verifier = uri.getQueryParameter("oauth_verifier");

        AccessToken accessToken = t.getOAuthAccessToken(this.requestToken, verifier);
        
        secretOAuth.getId().setToken(String.valueOf(accessToken.getUserId()));
        
        secretOAuth.getToken().setToken(accessToken.getToken());
        secretOAuth.getToken().setSecret(accessToken.getTokenSecret());

		secrets.save(secretOAuth.getId());
		secrets.save(secretOAuth.getToken());
	}

	@Override
	public void postStatus(Message msg) throws Exception {
		if (msg.getImageUrl() != null && msg.getImageUrl().length() > 0)
			this.postTweetWithImage(msg.getTitle(), msg.getImageUrl(), msg.getText());
		else if (msg.getUrl() != null && msg.getUrl().length() > 0)
			this.postTweet(msg.getText(), msg.getUrl());
		else
			this.postTweet(msg.getText());
	}
	
	@Override
	public boolean hasSecret() {
		return !secretOAuth.isBlank();
	}

	@Override
	public void addPeopleInNetwork() throws Exception {
		try {
			String name = user.getName();
			Relationship rel = twitter.showFriendship(name, getAppId());
			if (!rel.isSourceFollowingTarget())
				twitter.createFriendship(getAppId());
		}
		catch (Exception e) {
			twitter.createFriendship(getAppId());
		}
	}
	
	public String getUserName() {
		return user == null ? this.getCachedName() : user.getScreenName();
	}
	
	public String getUserAvatarUrl() {
		return user == null ? null : user.getProfileImageURL();
	}

	@Override
	public String getUserAlias() {
		return user == null ? this.getCachedAlias() : user.getName();
	}	
	
	@Override
	public void logout() {
		super.logout();
		
		user = null;
		twitter = null;
	}
}
