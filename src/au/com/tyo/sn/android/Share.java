package au.com.tyo.sn.android;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Log;

public class Share {
	
	private static final String LOG_TAG = "Share";
	
	private Activity activity;
	
	public Share(Activity activity) {
		this.activity = activity;
	}

	public Intent getShareIntent(String type, String subject, String text) {
	    Intent share = null;

	    List<ResolveInfo> resolveInfo = activity.getPackageManager().queryIntentActivities(share, 0);
	    if (!resolveInfo.isEmpty()){
	        for (ResolveInfo info : resolveInfo) {
	            if (info.activityInfo.packageName.toLowerCase().contains(type) || 
	                    info.activityInfo.name.toLowerCase().contains(type) ) {
	            	share = new Intent(android.content.Intent.ACTION_SEND);
	            	share.setType("text/plain");
	            	
	                share.putExtra(Intent.EXTRA_SUBJECT,  subject);
	                share.putExtra(Intent.EXTRA_TEXT, text);
	                
	                share.setPackage(info.activityInfo.packageName);

	                break;
	            }
	        }
	    }
        return share;
	}
}
