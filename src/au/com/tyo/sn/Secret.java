package au.com.tyo.sn;

public class Secret {
	
	private String token; // use for storing the token, could be user id
	
	private String secret; // token secret, could be the password for the correspondent id
	
	private int typeSn;  // social network type
	
	private int typeAuth;  // for oauth, OAUTH_CONSUMER or , OAUTH_ACCESS_TOKEN
	
	public Secret() {
		this("", "");
		this.typeSn = SocialNetwork.ANY;
	}
	
	public Secret(int type) {
		this("", "");
		this.typeSn = type;
	}
	
	public Secret(String token, String secret) {
		this.setToken(token);
		this.setSecret(secret);		
	}

	public Secret(int type, String token, String secret) {
		this(token, secret);
		this.typeSn = type;
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
		return typeSn;
	}

	public void setType(int type) {
		this.typeSn = type;
	}

	public int getTypeAuth() {
		return typeAuth;
	}

	public void setTypeAuth(int typeAuth) {
		this.typeAuth = typeAuth;
	}

	public String getTypeString() {
		return SocialNetwork.SOCIAL_NETWORK[typeSn];
	}
}
