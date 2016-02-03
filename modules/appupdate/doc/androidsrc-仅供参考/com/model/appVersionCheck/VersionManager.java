package com.model.appVersionCheck;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.view.View;

/**
 * APP更新类。 通过回调函数可以在各个更新节点实现更多功能
 * 
 * @author DJ
 * 
 */
public abstract class VersionManager {
	private int appid = 1;
	private String clientinfo;
	private String serverUrl = "";
	private static final String CHECK_URL = "/checkNewestAppVersion";
	private static final String DOWNLOAD_URL = "/downloadNewest";
	private Context context;
	private VersionUpdateCallBack versionUpdateCallBack;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param serverURL
	 *            服务器地址: 192.168.1.1:8080/项目名
	 * @param appid
	 * @param callback
	 */
	public VersionManager(Context context, String serverURL, int appid,
			String clientinfo, VersionUpdateCallBack callback) {
		this.appid = appid;
		this.context = context;
		this.serverUrl = serverURL;
		this.clientinfo = clientinfo;
		if (callback != null)
			versionUpdateCallBack = callback;
		else
			versionUpdateCallBack = new DefaultVersionUpdateCallBack();
	}

	/**
	 * 退出APP， 调用者请自行实现该功能，用于当强制更新时，如果用户不更新，强制退出APP
	 */
	public abstract void exitApp();

	/**
	 * 自动检查更新，常用于应用打开时进行更新检查,如果有更新且需要弹出窗口提示，则弹窗提醒
	 */
	public void autocheck() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appid", appid);
		param.put("versioncode", getVersionCode());
		new VersionCheckAsync(context, serverUrl + CHECK_URL, param) {

			protected void onPostExecute(AppVersionInfo appVersionInfo) {
				super.onPostExecute(appVersionInfo);
				// 检查更新失败
				if (appVersionInfo.getResultcode() == AppVersionInfo.RESULT_ERROR
						|| appVersionInfo.getResultcode() == AppVersionInfo.RESULT_NO_APP) {
					versionUpdateCallBack.callBack(
							VersionUpdateCallBack.CHECK_RESULT_ERROR,
							appVersionInfo);
				}
				// 未检测到新版本
				if (appVersionInfo.getResultcode() == AppVersionInfo.RESULT_NOTINGNEW) {
					versionUpdateCallBack.callBack(
							VersionUpdateCallBack.CHECK_RESULT_NOTING_UPDATE,
							appVersionInfo);
				}
				// 有新版本升级
				if (appVersionInfo.getResultcode() == AppVersionInfo.RESULT_NEWVERSION) {
					// 有更新，强制更新
					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_FORCE) {
						versionUpdateCallBack
								.callBack(
										VersionUpdateCallBack.CHECK_RESULT_UPDATE_TYPE_POP_FORCE,
										appVersionInfo);
					}
					// 有更新，用户选择
					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_AUTO) {
						versionUpdateCallBack
								.callBack(
										VersionUpdateCallBack.CHECK_RESULT_UPDATE_TYPE_POP_AUTO,
										appVersionInfo);
					}
					// 有更新，不弹出提醒
					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_MANUAL_MANUAL) {
						versionUpdateCallBack
								.callBack(
										VersionUpdateCallBack.CHECK_RESULT_UPDATE_TYPE_MANUAL_MANUAL,
										appVersionInfo);
					}
					// 如果不提示更新通知
					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_MANUAL_MANUAL)
						return;

					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setTitle("发现新版本 " + appVersionInfo.getVersionname());
					builder.setCancelable(false);
					builder.setPositiveButton("立即更新", null);
					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_FORCE) {
						builder.setNegativeButton("退出应用", null);
					} else {
						builder.setNegativeButton("暂不更新", null);
					}
					String msg = appVersionInfo.getUpdatelog();
					builder.setMessage(msg);
					AlertDialog alertDialog = builder.create();
					alertDialog.show();
					alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
							.setOnClickListener(
									new UpdateClickListener(appVersionInfo,
											alertDialog));

					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_FORCE)
						alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
								.setOnClickListener(
										new ExitAppClickListener(
												appVersionInfo, alertDialog));
					else
						alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
								.setOnClickListener(
										new DelayUpdateClickListener(
												appVersionInfo, alertDialog));
				}

			};
		}.execute();
	}

	/**
	 * 手机检查，常用于在应用设置中点击检查更新时进行检查, 展示检查结果，如果强制更新，则进行强制更新
	 */
	public void manucheck() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appid", appid);
		param.put("versioncode", getVersionCode());

		new VersionCheckAsync(context, serverUrl + CHECK_URL, param) {
			protected void onPostExecute(AppVersionInfo appVersionInfo) {
				super.onPostExecute(appVersionInfo);
				// 有新版本升级
				if (appVersionInfo.getResultcode() == AppVersionInfo.RESULT_NEWVERSION) {
					// 有更新，强制更新
					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_FORCE) {
						versionUpdateCallBack
								.callBack(
										VersionUpdateCallBack.CHECK_RESULT_UPDATE_TYPE_POP_FORCE,
										appVersionInfo);
					}
					// 有更新，用户选择
					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_AUTO) {
						versionUpdateCallBack
								.callBack(
										VersionUpdateCallBack.CHECK_RESULT_UPDATE_TYPE_POP_AUTO,
										appVersionInfo);
					}
					// 有更新，不弹出提醒
					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_MANUAL_MANUAL) {
						versionUpdateCallBack
								.callBack(
										VersionUpdateCallBack.CHECK_RESULT_UPDATE_TYPE_MANUAL_MANUAL,
										appVersionInfo);
					}

					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setTitle("发现新版本 " + appVersionInfo.getVersionname());
					builder.setPositiveButton("立即更新", null);
					builder.setCancelable(false);

					// 强制更新
					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_FORCE)
						builder.setNegativeButton("退出应用", null);
					else
						builder.setNegativeButton("暂不更新", null);
					String msg = appVersionInfo.getUpdatelog();
					builder.setMessage(msg);
					AlertDialog alertDialog = builder.create();
					alertDialog.show();

					alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
							.setOnClickListener(
									new UpdateClickListener(appVersionInfo,
											alertDialog));

					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_FORCE)
						alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
								.setOnClickListener(
										new ExitAppClickListener(
												appVersionInfo, alertDialog));
					else
						alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
								.setOnClickListener(
										new DelayUpdateClickListener(
												appVersionInfo, alertDialog));

				} else if (appVersionInfo.getResultcode() == AppVersionInfo.RESULT_NOTINGNEW) {// 无更新
					versionUpdateCallBack.callBack(
							VersionUpdateCallBack.CHECK_RESULT_NOTING_UPDATE,
							appVersionInfo);
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setMessage("暂无更新!");
					builder.setCancelable(false);
					builder.setPositiveButton("确定", null);
					builder.show();
				} else {
					versionUpdateCallBack.callBack(
							VersionUpdateCallBack.CHECK_RESULT_ERROR,
							appVersionInfo);
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setMessage("检查更新失败!");
					builder.setCancelable(false);
					builder.setPositiveButton("确定", null);
					builder.show();
				}
			}
		}.execute();
	}

	/**
	 * 只检查APP更新，不做任何其它弹窗等功能， 其它功能调用者需在回调函数中进行实现。 回调函数支持以下几种结果的回调
	 * 
	 * VersionUpdateCallBack.CHECK_RESULT_ERROR 检查更新出错,或无此APP的信息时
	 * VersionUpdateCallBack.CHECK_RESULT_NOTING_UPDATE 检查没有发现更新
	 * VersionUpdateCallBack.CHECK_RESULT_UPDATE_TYPE_POP_FORCE 检查到有更新，且类型为强制更新
	 * VersionUpdateCallBack.CHECK_RESULT_UPDATE_TYPE_POP_AUTO
	 * 检查到有更新，且类型为自动弹出更新通知，允许用户选择更新或不更新，可以继续使用
	 * VersionUpdateCallBack.CHECK_RESULT_UPDATE_TYPE_MANUAL_MANUAL
	 * 检查到有更新，且类型为不自动弹出更新通知
	 * 
	 * 回调函数中通过参数params[0] 获得更新信息AppVersionInfo appVersionInfo
	 */
	public void onlycheck() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appid", appid);
		param.put("versioncode", getVersionCode());
		new VersionCheckAsync(context, serverUrl + CHECK_URL, param) {

			protected void onPostExecute(AppVersionInfo appVersionInfo) {
				super.onPostExecute(appVersionInfo);
				// 检查更新失败
				if (appVersionInfo.getResultcode() == AppVersionInfo.RESULT_ERROR
						|| appVersionInfo.getResultcode() == AppVersionInfo.RESULT_NO_APP) {
					versionUpdateCallBack.callBack(
							VersionUpdateCallBack.CHECK_RESULT_ERROR,
							appVersionInfo);
				}
				// 未检测到新版本
				if (appVersionInfo.getResultcode() == AppVersionInfo.RESULT_NOTINGNEW) {
					versionUpdateCallBack.callBack(
							VersionUpdateCallBack.CHECK_RESULT_NOTING_UPDATE,
							appVersionInfo);
				}
				// 有新版本升级
				if (appVersionInfo.getResultcode() == AppVersionInfo.RESULT_NEWVERSION) {
					// 有更新，强制更新
					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_FORCE) {
						versionUpdateCallBack
								.callBack(
										VersionUpdateCallBack.CHECK_RESULT_UPDATE_TYPE_POP_FORCE,
										appVersionInfo);
					}
					// 有更新，用户选择
					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_AUTO) {
						versionUpdateCallBack
								.callBack(
										VersionUpdateCallBack.CHECK_RESULT_UPDATE_TYPE_POP_AUTO,
										appVersionInfo);
					}
					// 有更新，不弹出提醒
					if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_MANUAL_MANUAL) {
						versionUpdateCallBack
								.callBack(
										VersionUpdateCallBack.CHECK_RESULT_UPDATE_TYPE_MANUAL_MANUAL,
										appVersionInfo);
					}
				}
			};
		}.execute();
	}

	/**
	 * 安装apk
	 */
	public void installAPK(String APKPath, Context context) {
		File appFile = new File(APKPath);
		if (!appFile.exists()) {
			return;
		}

		// 跳转到新版本应用安装页面
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + appFile.toString()),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	class UpdateClickListener implements View.OnClickListener {
		private AppVersionInfo appVersionInfo;
		private AlertDialog dialog;

		public UpdateClickListener(AppVersionInfo appVersionInfo,
				AlertDialog dialog) {
			this.appVersionInfo = appVersionInfo;
			this.dialog = dialog;
		}

		@Override
		public void onClick(View v) {
			boolean cancelable = appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_FORCE ? false
					: true;
			String savepath = "";
			if (appVersionInfo.getDownloadpath() != null
					&& !appVersionInfo.getDownloadpath().equals("")) {
				savepath = appVersionInfo.getDownloadpath() + "/update.apk";
			} else
				savepath = SDFileUtils.getSDPATH() + "/update.apk";

			// 检查是不是已经下载
			if (checkMd5(savepath, appVersionInfo.getResmd5()) == false) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("appid", appid);
				param.put("versioncode", getVersionCode());
				param.put("imei", getImei());
				param.put("clientinfo", getClientInfo());

				new VersionDownloadAsync(context, serverUrl + DOWNLOAD_URL,
						param, savepath, cancelable, appVersionInfo,
						versionUpdateCallBack) {
					protected void onPostExecute(Integer result) {
						super.onPostExecute(result);// 调用父postexecute执行进度条的关闭
						if (result == 0
								&& checkMd5(savepath,
										appVersionInfo.getResmd5())) {// 下载成功并检查MD5
							versionUpdateCallBack.callBack(
									VersionUpdateCallBack.DOWNLOAD_SUCCESSFUL,
									appVersionInfo);
							if (appVersionInfo.getAutoinstall() == AppVersionInfo.AUTOINSTALL_YES)
								installAPK(super.savepath, context);

						} else {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									context);
							builder.setTitle("应用更新");
							builder.setMessage("下载失败");
							builder.setOnCancelListener(new OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									versionUpdateCallBack
											.callBack(
													VersionUpdateCallBack.DOWNLOAD_FAILED,
													appVersionInfo);
								}
							});
							builder.show();
						}
					}

				}.execute();
			} else {
				installAPK(savepath, context);
			}

			if (appVersionInfo.getUpdatetype() != AppVersionInfo.UPDATE_TYPE_POP_FORCE)
				dialog.dismiss();
		}
	}

	class DelayUpdateClickListener implements View.OnClickListener {
		private AppVersionInfo appVersionInfo;
		private AlertDialog dialog;

		public DelayUpdateClickListener(AppVersionInfo appVersionInfo,
				AlertDialog dialog) {
			this.appVersionInfo = appVersionInfo;
			this.dialog = dialog;
		}

		@Override
		public void onClick(View v) {
			versionUpdateCallBack.callBack(VersionUpdateCallBack.DELAY_UPDATE,
					appVersionInfo);
			dialog.dismiss();
		}
	}

	class ExitAppClickListener implements View.OnClickListener {
		private AppVersionInfo appVersionInfo;
		private AlertDialog dialog;

		public ExitAppClickListener(AppVersionInfo appVersionInfo,
				AlertDialog dialog) {
			this.appVersionInfo = appVersionInfo;
			this.dialog = dialog;
		}

		@Override
		public void onClick(View v) {
			versionUpdateCallBack.callBack(VersionUpdateCallBack.EXIT_APP,
					appVersionInfo);
			exitApp();
			dialog.dismiss();
		}
	}

	/**
	 * 获得安卓的版本号，该版本号定义在manifast文件中
	 * 
	 * @return
	 */
	public int getVersionCode() {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		int versionCode = -1;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (info != null)
			versionCode = info.versionCode;
		return versionCode;
	}

	public String getImei() {
		TelephonyManager tm = (TelephonyManager) this.context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 获取客户端信息
	 * 
	 * @return
	 */
	public String getClientInfo() {
		return this.clientinfo;
	}

	/**
	 * 检查文件md5
	 * 
	 * @param filepath
	 * @param md5
	 * @return
	 */
	private boolean checkMd5(String filepath, String md5) {
		if (filepath == null || md5 == null)
			return false;
		// 检查是不是已经下载
		String filemd5 = FileMd5Utils.getMd5ByFile(filepath);
		if (filemd5 != null
				&& filemd5.toLowerCase(Locale.getDefault()).equals(
						md5.toLowerCase(Locale.getDefault())))
			return true;
		return false;
	}
}
