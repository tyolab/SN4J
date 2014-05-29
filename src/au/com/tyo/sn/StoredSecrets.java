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

public abstract class StoredSecrets extends Secrets {
	
	public static final String PREF_STORED_SECRET_TYPES = "pref_sn4j_stored_secret_types";
	
	protected int[] types;
	
	private static StoredSecrets instance;
	
	public static void setInstance(StoredSecrets ins) {
		instance = ins;
	}
	
	public static StoredSecrets getInstance() {
		return instance;
	}
	
	public StoredSecrets() {
		super();
		
		types = null;
	}
	
	public void load() {

	}
	
	public void save() {

	}
	
	protected String typesToString() {
		StringBuffer sb = new StringBuffer();
		if (types.length > 0) {
			sb.append(types[0]);
			for (int i = 1; i < types.length; ++i) 
				sb.append("," + types[i]);
		}
		return sb.toString();
	}

}
