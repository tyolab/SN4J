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

package au.com.tyo.sn;

import java.util.HashMap;
import java.util.LinkedList;

import twitter4j.TwitterException;
import android.content.res.Resources.NotFoundException;

import au.com.tyo.sn.twitter.SNTwitter;

public class SocialNetwork implements SocialNetworkConstants {
	
	private SNTwitter twitter;
	
	private LinkedList<Message> queue; 
	
	private HashMap<Integer, SNBase> sns;
	
	private static SocialNetwork instance;
	
	public static SocialNetwork getInstance() {
		if (instance == null)
			instance = new SocialNetwork();
		return instance;
	}
	
	public SocialNetwork() {
		queue = new LinkedList<Message>();
	}

	public SNTwitter getTwitter() {
		return twitter;
	}

	public void setTwitter(SNTwitter twitter) {
		this.twitter = twitter;
	}
	
	public void setSocialNetwork(SNBase)
	
	public boolean hasLogedInSocialNetwork(int type) {
		if ((type & SocialNetworkConstants.TWITTER) == SocialNetworkConstants.TWITTER) 
			return twitter.isAuthenticated();
		return false;
	}
	
	public void getSocialNetworkAuthenticated(int type) throws NotFoundException, TwitterException {
		if ((type & SocialNetworkConstants.TWITTER) == SocialNetworkConstants.TWITTER) 
			twitter.authenticate();
	}

	public boolean share(final Message msg) throws NotFoundException, TwitterException {
		final int type = msg.getSocialNetworkToShare();
		
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				boolean successful = true;
//					try {
						if ((type & SocialNetworkConstants.TWITTER) == SocialNetworkConstants.TWITTER) {
//							if (!twitter.isAuthenticated())
//								twitter.authenticate();
							
							if (twitter.isAuthenticated()) {
								twitter.postTweet(msg.getText());
								return true;
							}
//							else
//								successful = false;
						}
						
//					} catch (NotFoundException | TwitterException e1) {
//						successful = false;
//					}			
//				
//				if (!successful && listener != null)
//					listener.onOnShareToSocialNetworkError();
//			}
//			
//		}).run();
		return false;
	}

	public SNBase getSocialNetwork(int type) {
		return sns.get(type);
	}
}
