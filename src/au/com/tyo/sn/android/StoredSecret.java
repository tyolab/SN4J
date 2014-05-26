package au.com.tyo.sn.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import au.com.tyo.sn.Secret;

public class StoredSecret extends Secret {

	public static final String PREF_STORED_SECRET_TOKEN = "pref_sn4j_stored_secret_token";
	
	public static final String PREF_STORED_SECRET_SECRET = "pref_sn4j_stored_secret_secret";
	
	private Context context;
	
	public StoredSecret(String token, String secret) {
		super(token, secret);
	}
	
	public StoredSecret(Context context) {
		this.context = context;
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		this.setToken(prefs.getString(PREF_STORED_SECRET_TOKEN, ""));
		this.setSecret(prefs.getString(PREF_STORED_SECRET_SECRET, ""));
	}

	public void save() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putString(PREF_STORED_SECRET_TOKEN, getToken());
		
		editor.putString(PREF_STORED_SECRET_SECRET, getSecret());
		
		editor.commit();
	}
}
