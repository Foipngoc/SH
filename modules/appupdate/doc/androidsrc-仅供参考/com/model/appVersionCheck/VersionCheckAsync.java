package com.model.appVersionCheck;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * 检查版本信息
 * 
 * @author DJ
 * 
 */
public class VersionCheckAsync extends AsyncTask<Void, Void, AppVersionInfo> {
	private ProgressDialog dialog = null;
	private String url;
	private Map<String, Object> params;

	public VersionCheckAsync(Context context, String url,
			Map<String, Object> params) {
		this.url = url;
		this.params = params;
		dialog = new ProgressDialog(context);
		dialog.setMessage("正在检查更新，请稍候...");
		dialog.setCancelable(false);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if (dialog != null)
			dialog.show();
	}

	@Override
	protected AppVersionInfo doInBackground(Void... p) {
		AppVersionInfo appVersionInfo = new AppVersionInfo();
		String result = "";
		try {
			result = this.post(url, params);
			if (result == null || result.equals("")) {
				appVersionInfo.setResultcode(-1);
				appVersionInfo.setAppdesc("从服务器获取数据失败");
			} else {
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data = null;
				data = (JSONObject) jsonParser.nextValue();
				if (data == null) {
					appVersionInfo.setResultcode(AppVersionInfo.RESULT_ERROR);
					appVersionInfo.setAppdesc("从服务器获取数据失败");
				} else {
					// 解析resultcode和resultdesc
					JSONObject resultJson = data.getJSONObject("result");

					int resultcode = resultJson.getInt("resultcode");
					String resultdesc = resultJson.getString("resultdesc");
					/**
					 * BaseResult(0, "不存在该应用");BaseResult(1, "已为最新版本");
					 * BaseResult(2, "有新版本可以升级");
					 * result.setObj(newestAppVersionInfo); BaseResult(0,
					 * "不存在该应用");
					 */
					appVersionInfo.setResultcode(resultcode);
					appVersionInfo.setResultdesc(resultdesc);
					// 有新版本，解析
					if (resultcode == AppVersionInfo.RESULT_NEWVERSION) {
						JSONObject mapJson = resultJson.getJSONObject("map");
						JSONObject appversioninfoJson = mapJson
								.getJSONObject("appversioninfo");
						JSONObject appinfoJson = mapJson
								.getJSONObject("appinfo");

						appVersionInfo.setAppid(appinfoJson.getInt("id"));
						appVersionInfo.setAppvid(appversioninfoJson
								.getInt("id"));
						appVersionInfo.setAppname(appinfoJson
								.getString("appname"));
						appVersionInfo.setAppdesc(appinfoJson
								.getString("appdesc"));
						appVersionInfo
								.setCreatedate(getDateByString(appinfoJson
										.getString("createdate")));
						appVersionInfo.setVersioncode(appversioninfoJson
								.getInt("versioncode"));
						appVersionInfo.setVersionname(appversioninfoJson
								.getString("versionname"));
						appVersionInfo.setResmd5(appversioninfoJson
								.getString("resmd5"));
						appVersionInfo.setUpdatelog(appversioninfoJson
								.getString("updatelog"));
						appVersionInfo
								.setUpdatedate(getDateByString(appversioninfoJson
										.getString("updatedate")));
						appVersionInfo.setUpdatetype(appversioninfoJson
								.getInt("updatetype"));
						appVersionInfo.setDownloadpath(appversioninfoJson
								.getString("downloadpath"));
						appVersionInfo.setAutoinstall(appversioninfoJson
								.getInt("autoinstall"));

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			appVersionInfo.setResultcode(AppVersionInfo.RESULT_ERROR);
			appVersionInfo.setAppdesc("从服务器获取数据失败");
		}
		return appVersionInfo;
	}

	@Override
	protected void onPostExecute(AppVersionInfo result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (dialog != null)
			dialog.dismiss();
	}

	public String post(String actionUrl, Map<String, Object> params)
			throws Exception {
		if (actionUrl == null || actionUrl.equals("") || params == null)
			throw new Exception();
		String sb2 = "";
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		try {
			URL uri = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setConnectTimeout(30 * 1000);
			conn.setReadTimeout(30 * 1000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
					+ ";boundary=" + BOUNDARY);

			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINEND);
				sb.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"" + LINEND);
				sb.append("Content-Type: text/plain; charset=" + CHARSET
						+ LINEND);
				sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
				sb.append(LINEND);
				sb.append(entry.getValue());
				sb.append(LINEND);
			}

			DataOutputStream outStream = new DataOutputStream(
					conn.getOutputStream());
			Log.i("--URL>>>>", sb.toString());
			outStream.write(sb.toString().getBytes());
			InputStream in = null;

			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();

			int res = conn.getResponseCode();
			if (res == 200) {
				in = conn.getInputStream();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));
				String line = "";
				for (line = br.readLine(); line != null; line = br.readLine()) {
					sb2 = sb2 + line;
				}
			}
			outStream.close();
			conn.disconnect();
			Log.i("--RESULT>>>>", sb2);
			return sb2;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 从格式为"yyyy-MM-dd HH:mm:ss" 的String 获得Date
	 * 
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date getDateByString(String timeString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(timeString);
		return date;
	}
}
