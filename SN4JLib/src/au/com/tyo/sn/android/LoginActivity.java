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

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import au.com.tyo.sn.R;
import au.com.tyo.sn.SNBase;
import au.com.tyo.sn.SocialNetwork;

public class LoginActivity extends Activity {
	
	private static final String LOG_TAG = "LoginActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		
		Uri uri = null;
		Intent intent = this.getIntent();
		if (intent != null && intent.getData() != null) 
			new  RetrieveSocialNetworkAccessTokenTask().execute(intent.getData());
		else
			finish();
	}
	
	public class RetrieveSocialNetworkAccessTokenTask extends
				AsyncTask<Uri, Void, Void> {

		@Override
		protected Void doInBackground(Uri... params) {
			Uri uri = params[0];
			
			// find out which social network we are dealing with
			String path = uri.getPath();
			
			int type = path.charAt(0) == '/' ? Integer.valueOf(path.substring(1)) : Integer.valueOf(path);
			
			SNBase sn = SocialNetwork.getInstance().getSocialNetwork(type);
			
			if (sn == null) {
				sn = SocialNetwork.createSocialNetwork(type);
			}
				
	        //handle returning from authenticating the user
	        if (uri != null && uri.toString().startsWith(sn.getCallback().toString())) {
	            String token = uri.getQueryParameter("oauth_token");
	            String verifier = uri.getQueryParameter("oauth_verifier");
			
	            try {
					sn.processAccessToken(token, verifier);
				} catch (Exception e) {
					Log.e(LOG_TAG, e.getLocalizedMessage() == null ? "Error in processing the twitter access token" : e.getLocalizedMessage());
				}
			}
			return null;
		}
	}
}
