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

import java.util.Collection;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import au.com.tyo.sn.Secret;
import au.com.tyo.sn.SecretBase;
import au.com.tyo.sn.SecretTwitter;
import au.com.tyo.sn.SocialNetwork;
import au.com.tyo.sn.StoredSecrets;

public class SecretsOnDroid extends StoredSecrets {
	
	private Context context;
	
	public SecretsOnDroid(Context context) {
		super();
		
		this.context = context;
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		String[] tokens = prefs.getString(PREF_STORED_SECRET_TYPES, "").split(",");
		
		if (tokens != null && tokens.length > 0) {
			types = new int[tokens.length];
			int count = 0;
			for (String token : tokens) 
				types[count++] = Integer.valueOf(token);
		}
		
		load();
	}

	public void load() {
		if (types != null)
			for (int type : types) {
				SecretOnDroid storedSecret = new SecretOnDroid(context);
				Secret secret = null;
				switch (type) {
					case SocialNetwork.TWITTER:			
						secret = new SecretTwitter(SocialNetwork.AUTHENTICATION_OAUTH_ACCESS_TOKEN);
						storedSecret.load(secret);
						break;
					case SocialNetwork.FACEBOOK:					
					case SocialNetwork.GOOGLE_PLUS:
					case SocialNetwork.LINKED_IN:
//						secret1 = new SecretBase(type, SocialNetwork.AUTHENTICATION_OAUTH_CONSUMER);
						secret = new SecretBase(type, SocialNetwork.AUTHENTICATION_OAUTH_ACCESS_TOKEN);
						break;	
					case SocialNetwork.ANY:
					default:
						secret = new SecretBase(type);
						break;
				}
				
				this.add(secret);
			}
	}

	public void save() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putString(PREF_STORED_SECRET_TYPES, typesToString());
		
		editor.commit();
		
		Collection<Secret> set = getSecrets().values();
		for (Secret secret : set) {
			SecretOnDroid storedSecret = new SecretOnDroid(context);
			storedSecret.save(secret);
		}
			
	}
}
