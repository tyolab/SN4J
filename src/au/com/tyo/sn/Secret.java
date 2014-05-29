package au.com.tyo.sn;

public interface Secret {

	int getType();

	String buildKey();

	void setToken(String string);

	void setSecret(String string);

	void setType(int int1);

	void setTypeAuth(int int1);

	String getToken();

	String getSecret();

	int getTypeAuth();
	
	boolean isBlank();

//	Secret createSecret(int type, String token, String secretStr);
}
