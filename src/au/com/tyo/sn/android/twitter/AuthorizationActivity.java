package au.com.tyo.sn.android.twitter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import au.com.tyo.sn.R;

public class AuthorizationActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.twitter_authorization);
		
		WebView wv = (WebView) findViewById(R.id.wv_twitter_authorization);
		
		
	}
	
	public static void startTwitterAuthorizationActivity(Context context) {
		
	}
}
