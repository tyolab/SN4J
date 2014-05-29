package au.com.tyo.sn;

public interface SocialNetwork {
	
	public static final int ANY = 0;
	
	public static final int TWITTER = 1 << 0;
	
	public static final int FACEBOOK = 1 << 1;
	
	public static final int GOOGLE_PLUS = 1 << 2;
	
	public static final int LINKED_IN = 1 << 3;
	
	public static final int INDEX_TWITTER = 0;
	
	public static final int INDEX_FACEBOOK = 1;
	
	public static final int INDEX_GOOGLE_PLUS = 2;
	
	public static final int INDEX_LINKED_IN = 3;
	
	public static final int INDEX_EMAIL = 4;
	
	public static final int INDEX_GMAIL = 5;
	
	public static final String ANY_STR = "any";
	
	public static final String TWITTER_STR = "twitter";
	
	public static final String FACEBOOK_STR = "facebook";
	
	public static final String GOOGLE_PLUS_STR = "google_plus";
	
	public static final String LINKED_IN_STR = "linkedin";
	
	public static final String EMAIL_STR = "email";
	
	public static final String GMAIL_STR = "gmail";
	
	public static final String[] SOCIAL_NETWORK = {
																				TWITTER_STR, 
																				FACEBOOK_STR, 
																				GOOGLE_PLUS_STR, 
																				LINKED_IN_STR, 
																				EMAIL_STR,
																				GMAIL_STR
																				};
	
	public static final int AUTHENTICATION_ANY = -1;
	public static final int AUTHENTICATION_PASSWORD = 0;
	public static final int AUTHENTICATION_OAUTH_CONSUMER = 1;
	public static final int AUTHENTICATION_OAUTH_ACCESS_TOKEN = 2;
}
