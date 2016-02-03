package com.module.appversioncheck.model;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class AppInfo {
	private int id; // 自增ID
	private String appname; // 应用名
	private String appdesc; // 应用描述
	private Date createdate; // 创建时间
	private int newestappvid; // 最新版版本
	private String applogo; // 应用logo地址
	private String applogomd5; // 应用logoMD5
	private String barcode; // 二维码图片
	private String barcodemd5; // 二维码图片MD5
	private String weixindlpg; // 微信页下载地址，解决微信扫描无法下载问题

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public int getNewestappvid() {
		return newestappvid;
	}

	public void setNewestappvid(int newestappvid) {
		this.newestappvid = newestappvid;
	}

	public String getApplogo() {
		return applogo;
	}

	public void setApplogo(String applogo) {
		this.applogo = applogo;
	}

	public String getApplogomd5() {
		return applogomd5;
	}

	public void setApplogomd5(String applogomd5) {
		this.applogomd5 = applogomd5;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBarcodemd5() {
		return barcodemd5;
	}

	public void setBarcodemd5(String barcodemd5) {
		this.barcodemd5 = barcodemd5;
	}

	public String getWeixindlpg() {
		return weixindlpg;
	}

	public void setWeixindlpg(String weixindlpg) {
		this.weixindlpg = weixindlpg;
	}
}
