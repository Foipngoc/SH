package com.module.appversioncheck.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.module.appversioncheck.dao.AppVersionInfoDao;
import com.module.appversioncheck.model.AppVersionInfo;

@Repository("appVersionInfoDao")
public class AppVersionInfoDaoImpl extends BaseDaoDB implements
		AppVersionInfoDao {
	/**
	 * 获得最大的版本号
	 * 
	 * @return
	 */
	public int getMaxVersionCode(int appid) {
		Object o = this.findUnique(new HQL(
				"select max(a.versioncode) from AppVersionInfo a where a.appid=?",appid));
		if (o != null)
			return (int)o;
		else
			return 0;
	}

	/**
	 * 通过应用及版本号查找版本
	 * @param appid
	 * @param vcode
	 * @return
	 */
	public AppVersionInfo queryAppVersionInfo(int appid, int vcode) {
		return (AppVersionInfo) this
				.findUnique(new HQL(
						"select a from AppVersionInfo a where a.appid=? and a.versioncode=?",
						appid, vcode));
	}

	/**
	 * 能找应用版本在smallvcode及bigvcode之间的所有版本信息
	 * 
	 * smallvcode < versioncode <= bigvcode
	 * 
	 * @param appid
	 * @param bigvcode
	 * @param smallvcode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AppVersionInfo> queryAppVersionInfos(int appid, int bigvcode,
			int smallvcode) {
		return (List<AppVersionInfo>) this
				.find(new HQL(
						"select a from AppVersionInfo a where a.appid=? and a.versioncode > ? and a.versioncode <= ?"
								+ " order by versioncode desc", appid,
						smallvcode, bigvcode)).getData();
	}
}
