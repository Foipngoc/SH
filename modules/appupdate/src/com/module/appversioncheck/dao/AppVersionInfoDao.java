package com.module.appversioncheck.dao;

import java.util.List;

import com.common.dao.BaseDao;
import com.module.appversioncheck.model.AppVersionInfo;

public interface AppVersionInfoDao extends BaseDao {
	/**
	 * 获得最大的版本号
	 * 
	 * @return
	 */
	public int getMaxVersionCode(int appid);

	/**
	 * 通过应用及版本号查找版本
	 * 
	 * @param appid
	 * @param vcode
	 * @return
	 */
	public AppVersionInfo queryAppVersionInfo(int appid, int vcode);

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
	public List<AppVersionInfo> queryAppVersionInfos(int appid, int bigvcode,
			int smallvcode);
}
