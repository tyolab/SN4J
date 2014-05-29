package au.com.tyo.sn;

public class Twitter {
	
	public static final String REQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token"; 
	public static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
	public static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
	
	private String consumerKey;
	
	private String consumerKeySecret;
	
	private String accessToken;
	
	private String accessTokenSecret;
	
	
	public Twitter() {
		
	}
	
	public synchronized String getConsumerKey() {
		return consumerKey;
	}

	public synchronized void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public synchronized String getConsumerKeySecret() {
		return consumerKeySecret;
	}

	public synchronized void setConsumerKeySecret(String consumerKeySecret) {
		this.consumerKeySecret = consumerKeySecret;
	}

	public synchronized String getAccessToken() {
		return accessToken;
	}

	public synchronized void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public synchronized String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public synchronized void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	protected void loadConsumerKey() {
		
	}
	
	protected void loadAccessToken() {
		
	}
	
	public String getAuthorizationUrl() {
		String url = null;
		return url;
	}
	
	public boolean updateStatus(String status, String mediaUrl) {
		return false;
	}

}
