package au.com.tyo.sn;

import java.util.Map;

import au.com.tyo.sn.android.StoredSecret;

public abstract class StoredSecrets {
	
	public static final String PREF_STORED_SECRET_TYPES = "pref_sn4j_stored_secret_types";
	
	protected int[] types;
	
	protected Map<Integer, StoredSecret> storedSecrets;
	
	public StoredSecrets() {

	}
	
	public void loadSecrets() {

	}
	
	public void save() {

	}
	
	private String typesToString() {
		StringBuffer sb = new StringBuffer();
		if (types.length > 0) {
			sb.append(types[0]);
			for (int i = 1; i < types.length; ++i) 
				sb.append("," + types[i]);
		}
		return sb.toString();
	}

}
