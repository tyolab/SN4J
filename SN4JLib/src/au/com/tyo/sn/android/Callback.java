/*
 * Copyright (C) 2014 TYONLINE TECHNOLOGY PTY. LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package au.com.tyo.sn.android;

import android.net.Uri;

public class Callback {
	
	public static final String CALLBACK_DEFAULT_SCHEME = "callback";
	
	public static final String CALLBACK_DEFAULT_HOST = "sn4j";
	
	private String scheme;
	
	private String host;
	
	private String path; // to show what social network for
	
	private static Callback callback;
	
	static {
		callback = new Callback();
	}
	
	public Callback() {
		this.scheme = CALLBACK_DEFAULT_SCHEME;
		this.host = CALLBACK_DEFAULT_HOST;
	}
	
	public Callback(String path) {
		this();
		this.path = path;
	}
	
	public Callback(String scheme, String host) {
		this.scheme = scheme;
		this.host = host;
	}
	
	public Callback(String scheme, String host, String path) {
		this(scheme, host);
		this.setPath(path);
	}
	
	public static Callback getDefaultCallback() {
		return callback;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Uri toUri() {
		return Uri.parse(scheme+"://" + host);
	}
	
	@Override
	public String toString() {
		return toUri().toString();
	}
}
