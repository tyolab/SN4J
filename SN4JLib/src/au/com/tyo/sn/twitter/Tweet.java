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

import au.com.tyo.sn.Status;

public class Tweet implements Status {
	
	public static final int CHARACTER_LIMIT = 140; 
	
	public static final int CHARACTER_NUMBER_TO_REMOVE = 3;
	
	public static final int CHARCTERS_NUMBER_MINIMUM = 20; // ?
	
	public static final String ELLIPSIS =  " ... ";
	
	protected StringBuffer buffer;  // the body text;
	
	private String prefix;
	
	private String signature;
	
	private String url;
	
	protected static int limit;
	
	private boolean trimmed;
	
	public Tweet() {
		buffer = new StringBuffer();
		limit = CHARACTER_LIMIT;
		trimmed = false;
	}
	
	public Tweet(String text) {
		this(text, "", "", "");
	}
	
	public Tweet(String text, String prefix, String signature, String url) {
		this();
		
		this.prefix = prefix;
		this.buffer.append(text);
		this.signature = signature;
		this.url = url;
	}
	
	public void preOccupy(int howManyChars) {
		limit -= howManyChars;
		if (limit < 0)
			limit = 0;
	}
	
	public static int getLimit() {
		return limit;
	}
	
	public static void setLimit(int limit) {
		Tweet.limit = limit;
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
		this.url = url;
//		if (buffer.length() + tempUrl.length() > getLimit()) 
//			tempUrl = GooGl.getShortenedUrl(tempUrl);
		
		int whereShouldBeTrim = getTextLengthShouldBe();
		if (buffer.length() > whereShouldBeTrim && trimToFit) {
			trimmed = true;
			shrinkToFit();
		}
		else
			this.appendOrNot(url);
	}
	
	public void shrinkToFit() {
		int whereShouldBeTrim = getTextLengthShouldBe();
		if (trimmed)
			whereShouldBeTrim -= ELLIPSIS.length(); 
		
		if (buffer.length() > whereShouldBeTrim && whereShouldBeTrim > 0) {
			int howmanytotrim = buffer.length() - whereShouldBeTrim;
			shrinkToFit(howmanytotrim);
		}
	}

	public void shrinkToFit(int number) {
		int i = buffer.length() - 1;
		for (; i > number; --i) {
			if (buffer.length() <= CHARCTERS_NUMBER_MINIMUM)
				break;
			buffer.deleteCharAt(i);
		}
		
		while (buffer.length() > 0 && Character.isLetterOrDigit(buffer.charAt(i)))
				buffer.deleteCharAt(i--);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	private int getTextLengthShouldBe() {
		return this.getTextLengthShouldBe(0);
	}
	
	private int getTextLengthShouldBe(int i) {
		return limit - url.length() - prefix.length() - signature.length() - i - (trimmed ? ELLIPSIS.length() : 0); 
	}
	
	public String toString() {
		String tweet = create();
		
		if (tweet.length() > limit) {
			trimmed = true;
			this.shrinkToFit();
			tweet = create();
		}
		return tweet;
	}
	
	public String create() {
		return prefix + buffer.toString() + (trimmed ? ELLIPSIS : "") + url + signature;
	}

	@Override
	public String getText() {
		return toString();
	}
}
