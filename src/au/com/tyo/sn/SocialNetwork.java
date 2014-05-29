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

import twitter4j.TwitterException;
import android.content.res.Resources.NotFoundException;
import au.com.tyo.sn.twitter.Twitter;

public class SocialNetwork {
	
	private Twitter twitter;
	
	private OnShareToSocialNetworkListener listener;
	
	public SocialNetwork() {
		listener = null;
	}

	public Twitter getTwitter() {
		return twitter;
	}

	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}

	public void share(final int type, final Message msg) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				boolean successful = true;
				if (twitter.isAuthenticated()) {
					try {
						twitter.authenticate();
						
						if (twitter.isAuthenticated()) {
							if ((type & SocialNetworkConstants.TWITTER) == SocialNetworkConstants.TWITTER)
								twitter.postTweet(msg.getText());
						}
						else
							successful = false;
						
					} catch (NotFoundException | TwitterException e1) {
						successful = false;
					}			
				}
				
				if (!successful)
					listener.onOnShareToSocialNetworkError();
			}
			
		}).run();

	}
}
