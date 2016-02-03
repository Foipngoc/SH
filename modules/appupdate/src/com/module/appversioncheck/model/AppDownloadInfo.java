package com.module.appversioncheck.model;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * APP下载信息
 * 
 * @author DJ
 * 
 */
public class AppDownloadInfo {
	private int id; // 自增主键
	private int appid; // 应用id
	private int oldappvid; // 原版本id
	private int appvid; // 更新后版本id
	private Date updatedate; // 更新时间
	private String ipaddr; // 客户IP地址
	private String macaddr; // 客户MAC地址
	private String imei; // 手机端imei号
	private String clientinfo; // 客户端信息

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public int getOldappvid() {
		return oldappvid;
	}

	public void setOldappvid(int oldappvid) {
		this.oldappvid = oldappvid;
	}

	public int getAppvid() {
		return appvid;
	}

	public void setAppvid(int appvid) {
		this.appvid = appvid;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getClientinfo() {
		return clientinfo;
	}

	public void setClientinfo(String clientinfo) {
		this.clientinfo = clientinfo;
	}

	public String getMacaddr() {
		return macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
}
