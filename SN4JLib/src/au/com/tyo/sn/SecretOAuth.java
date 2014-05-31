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

/*
 * The secret for storing the access token and token secret
 */
public class SecretOAuth extends SecretBase {
	
	private SecretBase idSecret;  // for storing the user id;

	public SecretOAuth(int type) {
		super(type);
		
		this.setTypeAuth(SocialNetworkConstants.AUTHENTICATION_OAUTH_ACCESS_TOKEN);
		
		setIdSecret(new SecretBase(type, SocialNetworkConstants.AUTHENTICATION_OAUTH_ID));
	}

	public SecretBase getIdSecret() {
		return idSecret;
	}

	public void setIdSecret(SecretBase idSecret) {
		this.idSecret = idSecret;
	}
	

}
