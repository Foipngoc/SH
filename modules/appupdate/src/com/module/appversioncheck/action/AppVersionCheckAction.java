package com.module.appversioncheck.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;
import com.common.action.result.BaseResult;
import com.module.appversioncheck.model.AppInfo;
import com.module.appversioncheck.model.AppVersionInfo;
import com.module.appversioncheck.service.AppVersionCheckService;

@Controller
public class AppVersionCheckAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7479732448969184794L;

	@Resource
	private AppVersionCheckService appVersionCheckService;

	private int appvid = -1;
	private int versioncode = -1; // 版本号
	private String versionname; // 版本名，用于显示
	private String updatelog; // 更新日志
	private int updatetype; // 该版本相对上一版本的更新方式
	private String downloadpath; // APP下载到本地路径
	private int autoinstall = -1; // 更新后是否自动安装
	private String weixinpage;

	private String imei; // 手机端Imei号
	private String clientinfo; // 更新客户端信息

	private int autoset;// 更新完自动安装
	private int autogenvcode; // 自动生成版本号

	private int appid = -1;
	private String appname;
	private String appdesc;

	private BaseResult result;
	private String filename;
	private List<File> file;
	private List<String> fileFileName;
	private List<String> fileContentType;
	private String resultcode;
	private String resultdesc;

	private String url;

	private long dlFilelength;
	private InputStream dlFile;
	private String dlFileName;

	public InputStream getDlFile() {
		return dlFile;
	}

	public long getDlFilelength() {
		return dlFilelength;
	}

	public String getDlFileName() {
		return dlFileName;
	}

	/**
	 * 获得所有已发布的应用信息
	 */
	public String queryApps() {
		List<?> appInfos = this.appVersionCheckService.queryApps();
		result = new BaseResult();
		result.addToMap("appinfos", appInfos);
		return SUCCESS;
	}

	/**
	 * 获得所有已发布的应用信息
	 */
	public String queryApp() {
		Map<String, Object> appInfo = this.appVersionCheckService
				.queryAppDetail(appid);
		result = new BaseResult();
		result.addToMap("appinfo", appInfo);
		return SUCCESS;
	}

	/**
	 * 发布新的应用
	 */
	public String publishApp() {
		if (appname == null || appname.equals("") || appdesc == null
				|| appdesc.equals("")) {
			result = new BaseResult(-1, "参数错误");
			return SUCCESS;
		}
		AppInfo appInfo = new AppInfo();
		appInfo.setAppname(appname);
		appInfo.setAppdesc(appdesc);
		if (weixinpage != null && !weixinpage.equals("")) {
			appInfo.setWeixindlpg(weixinpage);
		}
		result = this.appVersionCheckService.publishApp(appInfo,
				file == null ? null : file.get(0), fileFileName == null ? null
						: fileFileName.get(0));
		return SUCCESS;
	}

	/**
	 * 删除已发布的应用
	 * 
	 * @return
	 */
	public String delApp() {
		this.appVersionCheckService.delApp(appid);
		return SUCCESS;
	}

	/**
	 * 更新已发布应用
	 */
	public String updateApp() {
		result = this.appVersionCheckService.updateApp(appid, appname, appdesc,
				weixinpage, file == null ? null : file.get(0),
				fileFileName == null ? null : fileFileName.get(0));
		return SUCCESS;
	}

	/**
	 * 发布应用新版本
	 */
	public String publishAppVersion() {
		if (versioncode == -1 || versionname == null || versionname.equals("")
				|| updatelog == null || updatelog.equals("")
				|| downloadpath == null || downloadpath.equals("")
				|| autoinstall == -1)
			result = new BaseResult(-1, "参数错误");
		result = this.appVersionCheckService.publishAppVersion(appid,
				autogenvcode == 1 ? true : false, versioncode, versionname,
				updatelog, updatetype, autoset == 1 ? true : false,
				downloadpath, autoinstall, file.get(0), fileFileName.get(0));
		return SUCCESS;
	}

	/**
	 * 设置APP最新的版本为哪个
	 */
	public String updateNewestAppVersion() {
		result = this.appVersionCheckService.setNewestAppVersion(appid, appvid);
		return SUCCESS;
	}

	/**
	 * 更新应用已发布版本信息
	 */
	public String updateAppVersion() {
		result = this.appVersionCheckService.updateAppVersion(appvid,
				versionname, updatelog, updatetype, downloadpath, autoinstall,
				file == null ? null : file.get(0), fileFileName == null ? null
						: fileFileName.get(0));
		return SUCCESS;
	}

	/**
	 * 删除应用已发布的版本, 注： 如果当前用户已安装了被删除的版本，则用户更新时会提醒强制更新
	 */
	public String deleteAppVersion() {
		this.appVersionCheckService.deleteAppVersion(appvid);
		return SUCCESS;
	}

	/**
	 * 获取某应用发布的所有版本信息
	 */
	public String queryAppVersions() {
		List<AppVersionInfo> appVersionInfos = this.appVersionCheckService
				.queryAppVersions(appid);
		result = new BaseResult();
		result.addToMap("appversioninfos", appVersionInfos);
		return SUCCESS;
	}

	/**
	 * 获取某应用的某版本信息
	 */
	public String queryAppVersion() {
		AppVersionInfo appVersionInfoR = this.appVersionCheckService
				.queryAppVersion(appvid);
		result = new BaseResult();
		result.addToMap("appVersionInfo", appVersionInfoR);
		return SUCCESS;
	}

	/**
	 * 检查新版本信息
	 */
	public String checkNewestAppVersion() {
		result = this.appVersionCheckService.checkNewestAppVersion(appid,
				versioncode);
		return SUCCESS;
	}

	private String getRemoteAddress() {
		HttpServletRequest request = getHttpServletRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 下载最新版本
	 * 
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public String downloadNewestAppVersionRes() throws FileNotFoundException,
			UnsupportedEncodingException {
		String useragent = super.getHttpServletRequest()
				.getHeader("User-Agent");

		// 如果是微信，则自动重定向到下载页面
		if (useragent.contains("MicroMessenger")) {
			AppInfo appInfo = this.appVersionCheckService.queryApp(appid);
			weixinpage = "module/appversioncheck/weixindl.jsp";
			if (appInfo != null) {
				if (appInfo.getWeixindlpg() != null
						&& !appInfo.getWeixindlpg().equals("")) {
					weixinpage = appInfo.getWeixindlpg();
				}
			}

			weixinpage = weixinpage + "?appid=" + appid;
			return "weixin";
		}

		String ipaddr = null;
		String macaddr = null;
		ipaddr = getRemoteAddress();

		File file = this.appVersionCheckService.downloadNewestAppVersionRes(
				appid, versioncode, imei, ipaddr, macaddr, clientinfo);

		dlFile = new FileInputStream(file);
		dlFileName = file.getName();
		dlFilelength = file.length();
		dlFileName = filenameEncode(dlFileName);
		return "file";
	}

	public String downloadAppLogo() throws FileNotFoundException {
		File file = this.appVersionCheckService.downloadAppLogo(appid);
		dlFile = new FileInputStream(file);
		dlFileName = file.getName();
		dlFilelength = file.length();
		dlFileName = filenameEncode(dlFileName);
		return "file";
	}

	public String genBarCode() throws FileNotFoundException {
		result = this.appVersionCheckService.genBarCode(appid, url);
		return SUCCESS;
	}

	public String downloadBarCode() throws FileNotFoundException {
		File file = this.appVersionCheckService.downloadBarCode(appid);
		dlFile = new FileInputStream(file);
		dlFileName = file.getName();
		dlFilelength = file.length();
		dlFileName = filenameEncode(dlFileName);
		return "file";
	}

	/**
	 * 下载某版本版本资源
	 * 
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public String downloadAppVersionRes() throws FileNotFoundException,
			UnsupportedEncodingException {
		File file = this.appVersionCheckService.downloadAppVersionRes(appvid);
		dlFile = new FileInputStream(file);
		dlFileName = file.getName();
		dlFilelength = file.length();
		dlFileName = filenameEncode(dlFileName);
		return "file";
	}

	public BaseResult getResult() {
		return result;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public List<File> getFile() {
		return file;
	}

	public List<String> getFileFileName() {
		return fileFileName;
	}

	public List<String> getFileContentType() {
		return fileContentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public void setAppdesc(String appdesc) {
		this.appdesc = appdesc;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public void setAppvid(int appvid) {
		this.appvid = appvid;
	}

	public void setVersioncode(int versioncode) {
		this.versioncode = versioncode;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public void setUpdatelog(String updatelog) {
		this.updatelog = updatelog;
	}

	public void setDownloadpath(String downloadpath) {
		this.downloadpath = downloadpath;
	}

	public void setAutoinstall(int autoinstall) {
		this.autoinstall = autoinstall;
	}

	public void setUpdatetype(int updatetype) {
		this.updatetype = updatetype;
	}

	public void setClientinfo(String clientinfo) {
		this.clientinfo = clientinfo;
	}

	public void setAutogenvcode(int autogenvcode) {
		this.autogenvcode = autogenvcode;
	}

	public void setAutoset(int autoset) {
		this.autoset = autoset;
	}

	public String getResultcode() {
		return resultcode;
	}

	public String getResultdesc() {
		return resultdesc;
	}

	public int getAppid() {
		return appid;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getWeixinpage() {
		return weixinpage;
	}

	public void setWeixinpage(String weixinpage) {
		this.weixinpage = weixinpage;
	}
}
