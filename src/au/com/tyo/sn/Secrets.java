package au.com.tyo.sn;

import java.util.HashMap;
import java.util.Map;

public class Secrets {
	
	private Map<Integer, Secret> secrets;

	public Secrets() {
		secrets = new HashMap<Integer, Secret>();
	}
	
	public void add(int type, String token, String secretStr) {
		add(new Secret(type, token, secretStr));
	}
	
	public void add(Secret secret) {
		secrets.put(secret.getType(), secret);
	}
	
	public Secret get(int type) {
		return secrets.get(type);
	}
}
