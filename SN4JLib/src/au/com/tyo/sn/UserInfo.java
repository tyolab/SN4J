/*
 * Copyright (C) 2015 TYONLINE TECHNOLOGY PTY. LTD.
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
 * although user info is not considered secret, but we use it for caching the user id and image 
 */
public class UserInfo extends SecretBase {
	
	public UserInfo(int type, int subType) {
		super(type, subType);
	}

	public String getName() {
		return this.getToken();
	}
	
	public String getBase64EncodedImage() {
		return this.getSecret();
	}

	public void setName(String userName) {
		this.setToken(userName);
	}
	
	public void setBase64EncodedImage(String encodedImage) {
		this.setSecret(encodedImage);
	}
}
