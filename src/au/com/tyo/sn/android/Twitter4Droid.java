package au.com.tyo.sn.android;

import android.content.Context;
import au.com.tyo.sn.R;
import au.com.tyo.sn.Twitter;

public class Twitter4Droid extends Twitter {
	
	private Context context;
	
	public Twitter4Droid(Context context) {
		this.context = context;
	}

	@Override
	protected void loadConsumerKey() {
		this.setConsumerKey(context.getResources().getString(R.string.twitter_oauth_key));
		this.setConsumerKeySecret(context.getResources().getString(R.string.twitter_oauth_secret));
	}
	
	@Override
	protected void loadAccessToken() {
		this.setAccessToken(context.getResources().getString(R.string.twitter_access_token));
		this.setAccessTokenSecret(context.getResources().getString(R.string.twitter_access_token_secret));
	}
}
