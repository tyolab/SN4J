package au.com.tyo.sn;

public abstract class StoredSecrets extends Secrets {
	
	public static final String PREF_STORED_SECRET_TYPES = "pref_sn4j_stored_secret_types";
	
	protected int[] types;
	
	private static StoredSecrets instance;
	
	public static void setInstance(StoredSecrets ins) {
		instance = ins;
	}
	
	public static StoredSecrets getInstance() {
		return instance;
	}
	
	public StoredSecrets() {
		super();
		
		types = null;
	}
	
	public void load() {

	}
	
	public void save() {

	}
	
	protected String typesToString() {
		StringBuffer sb = new StringBuffer();
		if (types.length > 0) {
			sb.append(types[0]);
			for (int i = 1; i < types.length; ++i) 
				sb.append("," + types[i]);
		}
		return sb.toString();
	}

}
