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

import twitter4j.TwitterException;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import au.com.tyo.sn.R;
import au.com.tyo.sn.SecretTwitter;
import au.com.tyo.sn.SocialNetworkConstants;
import au.com.tyo.sn.twitter.SNTwitter;

public class Twitter4Droid extends SNTwitter {
	
	private Context context;
	
	public Twitter4Droid(Context context) {
		super();
		
		this.context = context;
		
		if (SecretSafe.getInstance() == null)
			SecretSafe.setInstance(new SecretSafe(context));
		
		this.setSecretSafe(SecretSafe.getInstance());
		
		this.loadSecretsFromSafe();
	}
	
	public void authenticate() throws NotFoundException, TwitterException {
		authenticate(context.getResources().getString(R.string.oauth_key_twitter), 
				context.getResources().getString(R.string.oauth_secret_twitter));
	}
	
	@Override
	protected void openAuthorizationURL(String authorizationUrl) {
		AuthorizationActivity.startTwitterAuthorizationActivity(context, authorizationUrl);
	}
}
