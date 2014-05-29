package au.com.tyo.sn;

public class SecretTwitter extends SecretBase {

	public SecretTwitter(int authType) {
		super(SocialNetwork.TWITTER);
		
		this.setTypeAuth(authType);
	}

}
