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

import java.util.LinkedList;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import au.com.tyo.sn.Message;
import au.com.tyo.sn.OnShareToSocialNetworkListener;
import au.com.tyo.sn.SocialNetwork;

public class SocialNetworkService extends Service {
	
    public final static int QUEUE_SIZE_LIMIT_DEFAULT = 50;
    
    public final static int QUEUE_SIZE_LIMIT_DEFAULT_ON_LOW_MEMORY = 10;
    
    public final static int ATTEMPTS_TO_TRY_BEFORE_GIVING_UP = 5;

	private final IBinder mBinder = new SocialNetworkBinder();
    
    private LinkedList<Message> queue;
    
    private boolean keepItRunning;
    
    private SocialNetwork sn;
	
	private OnShareToSocialNetworkListener listener;

	public class SocialNetworkBinder extends Binder {
        public SocialNetworkService getService() {
            return SocialNetworkService.this;
        }
    }

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	public boolean isServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (SocialNetworkService.class.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public void addMessage(Message msg) {
		synchronized (this) {
			queue.offer(msg);
		}
	}
	
    @Override
	public void onCreate() {
		super.onCreate();
		
		queue = new LinkedList<Message>();
		
		keepItRunning = true;
		
		listener = null;
		
		sn = SocialNetwork.getInstance();
		
		new  MessageHandlingTask().execute();
	}
	
	public void setOnShareToSocialNetworkListener(OnShareToSocialNetworkListener listener) {
		this.listener = listener;
	}
	
	public OnShareToSocialNetworkListener getOnShareToSocialNetworkListener() {
		return listener;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		keepItRunning = false;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	
	private class MessageHandlingTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			while (keepItRunning) {
					if (queue.size() > 0) {
						Message msg;
						
						boolean successful = false;
						
						synchronized (SocialNetworkService.this) {
							msg = queue.poll();
							
							if (msg != null)
								try {
									msg.setAttempts(msg.getAttempts() + 1);
									
									successful = sn.share(msg);
								}
								catch (Exception ex) {
									successful = false;
								}								
						}
						
						if (!successful) {
							if (msg.getAttempts() <= ATTEMPTS_TO_TRY_BEFORE_GIVING_UP)
								SocialNetworkService.this.addMessage(msg);
							else {
								if (listener != null)
									listener.onOnShareToSocialNetworkError();
							}
						}
						else 
							if (listener != null)
								listener.onOnShareToSocialNetworkSuccessfully(msg.getTitle());
				}
					
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			return null;
		}
		
	}

}
