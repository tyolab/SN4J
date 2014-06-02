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

package au.com.tyo.sn;

import au.com.tyo.sn.android.Callback;

public abstract class SNBase {
	
	protected Secrets secrets;
	
	protected int type;
	
	private Callback callback;
	
	public SNBase() {
		this(SocialNetwork.ANY);
	}
	
	public SNBase(int type) {
		this.type = type;
		
		setCallback(new Callback(String.valueOf(this.getType())));
	}

	public synchronized int getType() {
		return type;
	}

	public synchronized void setType(int type) {
		this.type = type;
	}

	public Secrets getSecretSafe() {
		return secrets;
	}

	public void setSecretSafe(Secrets secrets) {
		this.secrets = secrets;
	}

	public Callback getCallback() {
		return callback;
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public abstract void loadSecretsFromSafe();
	
	public abstract void saveSecretsToSafe();

	public abstract void processAccessToken(String token, String verifier)
			throws Exception;

	public abstract boolean isAuthenticated();

	public abstract void authenticate() throws Exception;

	public abstract void postStatus(Message msg);
}
