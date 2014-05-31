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
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import au.com.tyo.sn.R;
import au.com.tyo.sn.twitter.SNTwitter;

public class AuthorizationActivity extends Activity {
	
	private static final String AUTHORIZATION_URL = "AUTHORIZATION_URL";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.twitter_authorization);
		
		WebView wv = (WebView) findViewById(R.id.wv_twitter_authorization);
		
		Intent intent = this.getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			String url = intent.getStringExtra(AUTHORIZATION_URL);
			
			wv.loadUrl(url);
		}
		else
			finish();
	}
	
	public static void startTwitterAuthorizationActivity(Context context, String authorizationUrl) {
		Intent intent = new Intent(context, AuthorizationActivity.class);
		intent.putExtra(AUTHORIZATION_URL, authorizationUrl);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	public static void onResumeToMainActivity(Activity mainActivity, SNTwitter  twitter) throws TwitterException {
		
		  if (mainActivity.getIntent()!=null && mainActivity.getIntent().getData()!=null){
		        Uri uri = mainActivity.getIntent().getData();

		        //handle returning from authenticating the user
		        if (uri != null && uri.toString().startsWith(twitter.getCallback().toString())) {
		            String token = uri.getQueryParameter("oauth_token");
		            String verifier = uri.getQueryParameter("oauth_verifier");

		            twitter.processAccessToken(token, verifier);
		        }
		    }    
	}
}
