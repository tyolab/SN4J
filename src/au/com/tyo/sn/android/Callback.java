package au.com.tyo.sn.android;

import android.net.Uri;

public class Callback {
	
	private String scheme;
	
	private String host;
	
	public Callback(String scheme, String host) {
		this.scheme = scheme;
		this.host = host;
	}

	public Uri toUri() {
		return Uri.parse(scheme+"://" + host);
	}
}
