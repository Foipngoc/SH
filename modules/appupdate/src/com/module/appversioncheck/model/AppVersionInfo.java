package com.module.appversioncheck.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 每一个版本的配置文件
 * 
 * @author DJ
 * 
 */
public class AppVersionInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5312968214192019870L;
	private int id; // 自增主键
	private int appid; // 所属应用
	private int versioncode; // 版本号
	private String versionname; // 版本名，用于显示
	private String updatelog; // 更新日志
	private Date updatedate; // 更新时间
	private String respath; // 资源路径
	private String resmd5;// 资源MD5
	private int updatetype; // 该版本相对上一版本的更新方式

	private String downloadpath; // APP下载到本地路径
	private int autoinstall; // 更新后是否自动安装

	//自动安装
	public static final int AUTOINSTALL_YES = 1;
	
	//不自动安装
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getRespath() {
		return respath;
	}

	public void setRespath(String respath) {
		this.respath = respath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersioncode() {
		return versioncode;
	}

	public void setVersioncode(int versioncode) {
		this.versioncode = versioncode;
	}

	public int getUpdatetype() {
		return updatetype;
	}

	public void setUpdatetype(int updatetype) {
		this.updatetype = updatetype;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public String getDownloadpath() {
		return downloadpath;
	}

	public void setDownloadpath(String downloadpath) {
		this.downloadpath = downloadpath;
	}

	public String getResmd5() {
		return resmd5;
	}

	public void setResmd5(String resmd5) {
		this.resmd5 = resmd5;
	}

	public AppVersionInfo cloneObject() throws IOException,
			ClassNotFoundException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(this);

		ByteArrayInputStream byteIn = new ByteArrayInputStream(
				byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);

		return (AppVersionInfo) in.readObject();
	}

	public int getAutoinstall() {
		return autoinstall;
	}

	public void setAutoinstall(int autoinstall) {
		this.autoinstall = autoinstall;
	}
}
