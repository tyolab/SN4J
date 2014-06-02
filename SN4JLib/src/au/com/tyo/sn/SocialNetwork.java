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
import java.util.Map;

import au.com.tyo.sn.twitter.SNTwitter;

public class SocialNetwork implements SocialNetworkConstants {
	
//	private SNTwitter twitter;
	
	private LinkedList<Message> queue; 
	
	private Map<Integer, SNBase> sns;
	
	private static SocialNetwork instance;
	
	public static SocialNetwork getInstance() {
		if (instance == null)
			instance = new SocialNetwork();
		return instance;
	}
	
	public SocialNetwork() {
		queue = new LinkedList<Message>();
		sns = new HashMap<Integer, SNBase>();
	}

	public SNTwitter getTwitter() {
		return (SNTwitter) sns.get(SocialNetwork.TWITTER);
	}

	public void setTwitter(SNTwitter twitter) {
//		this.twitter = twitter;
		this.setSocialNetwork(twitter);
	}
	
	public void setSocialNetwork(SNBase sn) {
		sns.put(sn.getType(), sn);
	}
	
	public boolean hasLogedInSocialNetwork(int type) {
		SNBase sn = this.getSocialNetwork(type);
		return sn.isAuthenticated();
	}
	
	public void getSocialNetworkAuthenticated(int type) throws Exception {
		SNBase sn = this.getSocialNetwork(type);
		sn.authenticate();
	}

	public boolean share(final Message msg) throws Exception {
		final int type = msg.getSocialNetworkToShare();
		
		for (int what : SUPPORTED_SOCIAL_NETWORKS) 
			if ((type & what) == what) {

				SNBase sn = getSocialNetwork(what);
				if (sn.isAuthenticated()) {
					sn.postStatus(msg);
				}
			}
		return true;
	}

	public SNBase getSocialNetwork(int type) {
		return sns.get(type);
	}

	public static SNBase createSocialNetwork(int type) {
		/*
		 * TODO
		 *   create other network classes
		 */
		SNBase sn = null;
		switch (type) {
		case SocialNetwork.TWITTER:
			sn = new SNTwitter();
			break;
		case SocialNetwork.FACEBOOK:
//			sn = new SNBase(SocialNetwork.FACEBOOK);
			break;
		case SocialNetwork.GOOGLE_PLUS:
//			sn = new SNBase(SocialNetwork.FACEBOOK);
			break;		
		case SocialNetwork.LINKED_IN:
//			sn = new SNBase(SocialNetwork.FACEBOOK);
			break;				
		case SocialNetwork.ANY:
		default:
				break;
		}
		return sn;
	}
}
