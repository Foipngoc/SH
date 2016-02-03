package com.model.appVersionCheck;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;

/**
 * 下载最新版本
 * 
 * @author DJ
 * 
 */
public abstract class VersionDownloadAsync extends
		AsyncTask<Void, Integer, Integer> {
	private ProgressDialog dialog;
	private String url;
	private Map<String, Object> params;
	protected String savepath;
	protected boolean cancelable;
	private VersionUpdateCallBack versionUpdateCallBack;

	private AppVersionInfo appVersionInfo;

	public VersionDownloadAsync(Context context, String url,
			Map<String, Object> params, String savepath, boolean cancelable,
			AppVersionInfo appVersionInfo, VersionUpdateCallBack callBack) {
		this.url = url;
		this.cancelable = cancelable;
		this.params = params;
		this.savepath = savepath;
		this.appVersionInfo = appVersionInfo;
		this.versionUpdateCallBack = callBack;

		dialog = new ProgressDialog(context);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		if (this.cancelable) {
			dialog.setCancelable(true);
			dialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					VersionDownloadAsync.this.cancel(true);
					versionUpdateCallBack.callBack(
							VersionUpdateCallBack.DOWNLOAD_CANCEL,
							VersionDownloadAsync.this.appVersionInfo);
				}
			});
		} else
			dialog.setCancelable(false);
		dialog.setTitle("下载中");
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		dialog.setProgress(values[0]);
		versionUpdateCallBack.callBack(VersionUpdateCallBack.DOWNLOADING,
				values[0]);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.show();
		versionUpdateCallBack.callBack(VersionUpdateCallBack.BEGIN_DOWNLOAD,
				appVersionInfo);
	}

	@Override
	protected Integer doInBackground(Void... p) {
		HttpClient httpClient = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 15 * 1000);// 连接时间
			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 15 * 1000);// 数据传输时间
			HttpPost httpRequest = new HttpPost(url);// 请求地址
			List<NameValuePair> urlparams = new ArrayList<NameValuePair>();
			for (Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey();
				Object val = entry.getValue();
				urlparams.add(new BasicNameValuePair(key, val.toString()));
			}
			httpRequest.setEntity(new UrlEncodedFormEntity(urlparams));
			HttpResponse response = httpClient.execute(httpRequest);
			Header[] headers = response.getAllHeaders();
			long size = 0;// 文件大小
			for (Header h : headers) {
				if ("Content-Disposition".equals(h.getName())) {
					String fileString = h.getValue();
					fileString = fileString.substring(fileString
							.indexOf("filename=") + "filename=".length());
					fileString = fileString.replaceAll("\"", "");
					fileString = URLDecoder.decode(fileString, "UTF-8");
					Log.d("Content-Disposition", fileString);
				} else if ("Content-Length".equals(h.getName())) {
					size = Long.valueOf(h.getValue());
				}
			}
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return -1;
			}
			if (size < 0) {
				return -1;
			}
			HttpEntity resEntity = response.getEntity();
			is = resEntity.getContent();// 获得文件的输入流
			bis = new BufferedInputStream(is);
			File newFile = new File(savepath);
			fos = new FileOutputStream(newFile);
			bos = new BufferedOutputStream(fos);

			byte[] bytes = new byte[4096];
			int len = 0;// 最后一次的长度可能不足4096

			long readedLength = 0l;
			while ((len = bis.read(bytes)) > 0) {
				bos.write(bytes, 0, len);

				readedLength += len;

				int progress = (int) (readedLength * 100.0 / size);
				this.publishProgress(progress);
			}
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
			try {
				if (bis != null)
					bis.close();
			} catch (IOException e) {
			}
			try {
				if (bos != null)
					bos.close();
			} catch (IOException e) {
			}
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
			}
			httpClient.getConnectionManager().shutdown();
		}
		return 0;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		dialog.dismiss();
	}
}
