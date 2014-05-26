package au.com.tyo.sn.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import au.com.tyo.sn.Secret;
import au.com.tyo.sn.SocialNetwork;

public class StoredSecret extends Secret {

	public static final String PREF_STORED_SECRET_TOKEN = "pref_sn4j_stored_secret_token";
	
	public static final String PREF_STORED_SECRET_SECRET = "pref_sn4j_stored_secret_secret";
	
	private Context context;
	
	private String prefTokenStr;
	
	private String prefSecretStr;
	
	public StoredSecret(Context context) {
		this(context, SocialNetwork.ANY);
	}
	
	public StoredSecret(Context context, int type) {
		super(type);
		
		this.context = context;
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		prefTokenStr = PREF_STORED_SECRET_TOKEN +"_" + this.getTypeString();
		
		prefSecretStr = PREF_STORED_SECRET_SECRET +"_" + this.getTypeString();
		
		this.setToken(prefs.getString(prefTokenStr, ""));
		this.setSecret(prefs.getString(prefSecretStr, ""));
	}

	public void save() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putString(prefTokenStr, getToken());
		
		editor.putString(prefSecretStr , getSecret());
		
		editor.commit();
	}
}
