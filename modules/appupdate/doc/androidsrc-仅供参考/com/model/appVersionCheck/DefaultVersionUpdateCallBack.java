package com.model.appVersionCheck;

import android.util.Log;

public class DefaultVersionUpdateCallBack extends VersionUpdateCallBack {
	private static final String TAG = "DefaultVersionUpdateCallBack";

	@Override
	public void callBack(int status, Object... params) {
		Log.d(TAG, "Status:" + status);
	}

}
