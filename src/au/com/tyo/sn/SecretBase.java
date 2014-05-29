package au.com.tyo.sn;

public class SecretBase implements Secret {
	
	private String token; // use for storing the token, could be user id
	
	private String secret; // token secret, could be the password for the correspondent id
	
	private int typeSn;  // social network type
	
	private int typeAuth;  // for oauth, OAUTH_CONSUMER or , OAUTH_ACCESS_TOKEN
	
	public SecretBase() {
		this("", "");
	}
	
	public SecretBase(int type) {
		this(type, "", "");
	}
	
	public SecretBase(String token, String secret) {
		this.setToken(token);
		this.setSecret(secret);		
		this.typeSn = SocialNetwork.ANY;
		this.typeAuth = SocialNetwork.AUTHENTICATION_ANY;
	}

	public SecretBase(int type, String token, String secret) {
		this(token, secret);
		this.typeSn = type;
	}

	public SecretBase(int type, int typeAuth) {
		this(type, "", "");
		this.typeAuth = typeAuth;
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
		return getTypeString(typeSn);
	}
	
	public static String getTypeString(int type) {
		return SocialNetwork.SOCIAL_NETWORK[type];
	}

	@Override
	public String buildKey() {
		 return buildKey(this.getType(), this.getTypeAuth());
	}
	
	public static String buildKey(int type, int authType) {
		return type + "_" + authType;
	}

	@Override
	public boolean isBlank() {
		return token.length() == 0 || secret.length() == 0;
	}
}
