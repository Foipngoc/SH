package com.model.appVersionCheck;

import java.util.Date;

/**
 * 当前应用版本信息
 * 
 * @author DJ
 * 
 */
public class AppVersionInfo {
	private int resultcode; // 请求结果代码
	private String resultdesc; // 请求结果描述

	private int appid; // 应用ID
	private int appvid; // 当前版本ID

	/**
	 * 应用信息
	 */
	private String appname; // 应用名
	private String appdesc; // 应用描述
	private Date createdate; // 创建时间

	/**
	 * 版本信息
	 */
	private int versioncode; // 版本号
	private String versionname; // 版本名，用于显示
	private String resmd5;// 下载文件md5
	private String updatelog; // 更新日志
	private Date updatedate; // 更新时间

	/**
	 * 版本更新控制
	 */
	private int updatetype; // 该版本相对上一版本的更新方式
	private String downloadpath; // APP下载到本地路径
	private int autoinstall; // 更新后是否自动打开

	public static final int AUTOINSTALL_YES = 1;
	public static final int AUTOINSTALL_NO = 0;

	/**
	 * 自动弹出更新通知，且要求用户强制更新，如用户选择不更新则不允许使用，直接退出。
	 */
	public static final int UPDATE_TYPE_POP_FORCE = 0;

	/**
	 * 自动弹出更新通知，允许用户选择更新或不更新，可以继续使用。
	 */
	public static final int UPDATE_TYPE_POP_AUTO = 1;

	/**
	 * 不自动弹出更新通知，在用户点击检查更新之后弹出更新通知，允许用户选择更新或不更新
	 */
	public static final int UPDATE_TYPE_MANUAL_MANUAL = 2;

	/**
	 * resultcode有新版本升级
	 */
	public static final int RESULT_NEWVERSION = 2;
	
	/**
	 * resultcode已为最新版本
	 */
	public static final int RESULT_NOTINGNEW = 1;
	
	/**
	 * resultcode已为出错
	 */
	public static final int RESULT_ERROR = -1;
	
	/**
	 * resultcode不在在该应用
	 */
	public static final int RESULT_NO_APP = 0;
	
	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public int getAppvid() {
		return appvid;
	}

	public void setAppvid(int appvid) {
		this.appvid = appvid;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getAppdesc() {
		return appdesc;
	}

	public void setAppdesc(String appdesc) {
		this.appdesc = appdesc;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public int getVersioncode() {
		return versioncode;
	}

	public void setVersioncode(int versioncode) {
		this.versioncode = versioncode;
	}

	public String getVersionname() {
		return versionname;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public String getUpdatelog() {
		return updatelog;
	}

	public void setUpdatelog(String updatelog) {
		this.updatelog = updatelog;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public int getUpdatetype() {
		return updatetype;
	}

	public void setUpdatetype(int updatetype) {
		this.updatetype = updatetype;
	}

	public String getDownloadpath() {
		return downloadpath;
	}

	public void setDownloadpath(String downloadpath) {
		this.downloadpath = downloadpath;
	}

	public int getResultcode() {
		return resultcode;
	}

	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}

	public String getResultdesc() {
		return resultdesc;
	}

	public void setResultdesc(String resultdesc) {
		this.resultdesc = resultdesc;
	}

	public String getResmd5() {
		return resmd5;
	}

	public void setResmd5(String resmd5) {
		this.resmd5 = resmd5;
	}

	public int getAutoinstall() {
		return autoinstall;
	}

	public void setAutoinstall(int autoinstall) {
		this.autoinstall = autoinstall;
	}

}
