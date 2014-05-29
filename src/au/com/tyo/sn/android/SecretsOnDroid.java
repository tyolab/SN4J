package au.com.tyo.sn.android;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import au.com.tyo.sn.StoredSecrets;

public class SecretsOnDroid extends StoredSecrets {
	

	private Context context;
	
	public SecretsOnDroid(Context context) {
		this.context = context;
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		String[] tokens = prefs.getString(PREF_STORED_SECRET_TYPES, "").split(",");
		
		if (tokens != null && tokens.length > 0) {
			types = new int[tokens.length];
			int count = 0;
			for (String token : tokens) 
				types[count++] = Integer.valueOf(token);
		}
		
		loadSecrets();
	}

	
	public void loadSecrets() {
		storedSecrets = new HashMap<Integer, StoredSecret>();
		
		if (types != null)
			for (int type : types) {
				StoredSecret secret = new StoredSecret(context, type);
				storedSecrets.put(type, secret);
			}
	}
	
	public void save() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putString(PREF_STORED_SECRET_TYPES, typesToString());
		
		editor.commit();
	}
}
