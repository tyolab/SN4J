package au.com.tyo.sn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Secrets {
	
	private Map<String, Secret> secrets;

	public Secrets() {
		setSecrets(new HashMap<String, Secret>());
	}
	
//	public void add(int type, String token, String secretStr) {
//		add(new Secret(type, token, secretStr));
//	}
	
	public void add(Secret secret) {
		getSecrets().put(secret.buildKey(), secret);
	}
	
	public Secret get(int type, int authType) {
		return getSecrets().get(SecretBase.buildKey(type, authType));
	}
	
	public void addAll(ArrayList<Secret> list) {
		for (Secret secret : list)
			add(secret);
	}

	public Map<String, Secret> getSecrets() {
		return secrets;
	}

	public void setSecrets(Map<String, Secret> secrets) {
		this.secrets = secrets;
	}
}
