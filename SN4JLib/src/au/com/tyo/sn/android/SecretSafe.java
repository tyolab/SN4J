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

package au.com.tyo.sn.android;

import android.content.Context;
import au.com.tyo.sn.Secret;
import au.com.tyo.sn.StoredSecrets;

public class SecretSafe extends StoredSecrets {
	
	private SecretOnDroid storedSecret;
	
//	private SharedPreferences prefs;
	
	public SecretSafe(Context context) {
		super();
		
		storedSecret = new SecretOnDroid(context);
		
//		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
//		String value = prefs.getString(PREF_STORED_SECRET_TYPES, "");
//		
//		if (value != null && value.trim().length() > 0) {
//			String[] tokens = value.split(",");
//			
//			if (tokens != null && tokens.length > 0) {
//				types = new int[tokens.length];
//				int count = 0;
//				for (String token : tokens) {
//					if (token.trim().length() == 0)
//						continue;
//					types[count++] = Integer.valueOf(token);
//				}
//			}
//		}
		
//		load();
	}

//	public void load() {
//		if (types != null)
//			for (int type : types) {
//				Secret secret = null;
//				switch (type) {
//					case SocialNetworkConstants.TWITTER:			
//						secret = new SecretTwitter(SocialNetworkConstants.AUTHENTICATION_OAUTH_ACCESS_TOKEN);
//						storedSecret.load(secret);
//						break;
//					case SocialNetworkConstants.FACEBOOK:					
//					case SocialNetworkConstants.GOOGLE_PLUS:
//					case SocialNetworkConstants.LINKED_IN:
////						secret1 = new SecretBase(type, SocialNetwork.AUTHENTICATION_OAUTH_CONSUMER);
//						secret = new SecretBase(type, SocialNetworkConstants.AUTHENTICATION_OAUTH_ACCESS_TOKEN);
//						break;	
//					case SocialNetworkConstants.ANY:
//					default:
//						secret = new SecretBase(type);
//						break;
//				}
//				
//				this.add(secret);
//			}
//	}
//
//	public void save() {
//		SharedPreferences.Editor editor = prefs.edit();
//		
//		editor.putString(PREF_STORED_SECRET_TYPES, typesToString());
//		
//		editor.commit();
//		
//		Collection<Secret> set = getSecrets().values();
//		for (Secret secret : set) 
//			save(secret);
//	}
	
	public void load(Secret secret) {
		storedSecret.load(secret);
	}
	
	public void save(Secret secret) {
		storedSecret.save(secret);
	}
}