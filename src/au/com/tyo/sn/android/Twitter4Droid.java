package au.com.tyo.sn.android;

import android.content.Context;
import au.com.tyo.sn.R;
import au.com.tyo.sn.Secret;
import au.com.tyo.sn.SecretTwitter;
import au.com.tyo.sn.SocialNetwork;
import au.com.tyo.sn.StoredSecrets;
import au.com.tyo.sn.Twitter;

public class Twitter4Droid extends Twitter {
	
	private Context context;
	
	public Twitter4Droid(Context context) {
		super();
		
		this.context = context;
		
		secret = (SecretTwitter) SecretsOnDroid.getInstance().get(SocialNetwork.TWITTER, 
																			SocialNetwork.AUTHENTICATION_OAUTH_ACCESS_TOKEN);
	}
	
	@Override
	protected void loadAccessToken() {
		this.setAccessToken(context.getResources().getString(R.string.twitter_access_token));
		this.setAccessTokenSecret(context.getResources().getString(R.string.twitter_access_token_secret));
	}
}
