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

package au.com.tyo.sn.twitter;

import au.com.tyo.sn.shortener.GooGl;

public class Tweet {
	
	public static final int CHARACTER_LIMIT = 140;
	
	protected StringBuffer buffer;
	
	protected int limit;
	
	public Tweet() {
		buffer = new StringBuffer();
		limit = CHARACTER_LIMIT;
	}
	
	public void preOccupy(int howManyChars) {
		limit -= howManyChars;
		if (limit < 0)
			limit = 0;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public void appendOrNot(String what) {
		appendOrNot(what, " ");
	}
	
	public void appendOrNot(String what, String prefix) {
		if (buffer.length() + what.length() <= getLimit())
			buffer.append(prefix + what);
	}
	
	public void insertOrNot(String what) {
		insertOrNot(what, " ");
	}
	
	public void insertOrNot(String what, String suffix) {
		if (buffer.length() + what.length() <= getLimit())
			buffer.insert(0, what + suffix);
	}
	
	public void appendUrl(String url, boolean trimToFit) {
		appendUrl(url, trimToFit, -1);
	}
	
	public void appendUrl(String url, boolean trimToFit, int minLength) {
		String tempUrl = url;
//		if (buffer.length() + tempUrl.length() > getLimit()) 
//			tempUrl = GooGl.getShortenedUrl(tempUrl);
		
		if (buffer.length() + tempUrl.length() > getLimit() && trimToFit) {
			String endStr = " ... " + tempUrl;
			int whereShouldBeTrim = getLimit() - endStr.length();
			
			if (buffer.length() > whereShouldBeTrim && whereShouldBeTrim > 0) {
				int i = buffer.length() - 1;
				for (; i > whereShouldBeTrim; --i) {
					if (minLength > 0 && i <= minLength)
						break;
					buffer.deleteCharAt(i);
				}
				
				while (Character.isLetterOrDigit(buffer.charAt(i)))
						buffer.deleteCharAt(i--);
			}
			buffer.append(endStr);
		}
		else
			this.appendOrNot(tempUrl);
//		buffer.append(" " + tempUrl);

	}
}
