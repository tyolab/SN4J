package au.com.tyo.sn;

public class Secret {
	
	private String token; // use for storing the token 
	
	private String secret; // token secret
	
	private int type;
	
	public Secret() {
		this("", "");
		this.type = SocialNetwork.ANY;
	}
	
	public Secret(int type) {
		this("", "");
		this.type = type;
	}
	
	public Secret(String token, String secret) {
		this.setToken(token);
		this.setSecret(secret);
	}

	public Secret(int type, String token, String secret) {
		this(token, secret);
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
}
