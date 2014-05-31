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

import java.util.LinkedList;

import twitter4j.TwitterException;
import android.content.res.Resources.NotFoundException;
import au.com.tyo.sn.twitter.SNTwitter;

public class SocialNetwork {
	
	private SNTwitter twitter;
	
	private OnShareToSocialNetworkListener listener;
	
	private LinkedList<Message> queue; 
	
	public SocialNetwork() {
		listener = null;
	}

	public SNTwitter getTwitter() {
		return twitter;
	}

	public void setTwitter(SNTwitter twitter) {
		this.twitter = twitter;
	}
	
	public void setOnShareToSocialNetworkListener(OnShareToSocialNetworkListener listener) {
		this.listener = listener;
	}

	public void share(final int type, final Message msg) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				boolean successful = true;
					try {
						if ((type & SocialNetworkConstants.TWITTER) == SocialNetworkConstants.TWITTER) {
							if (!twitter.isAuthenticated())
								twitter.authenticate();
							
							if (twitter.isAuthenticated()) 
								twitter.postTweet(msg.getText());
							else
								successful = false;
						}
						
					} catch (NotFoundException | TwitterException e1) {
						successful = false;
					}			
				
				if (!successful && listener != null)
					listener.onOnShareToSocialNetworkError();
			}
			
		}).run();

	}
}
