package com.module.appversioncheck.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.module.appversioncheck.dao.AppDownloadInfoDao;

@Repository("appDownloadInfoDao")
public class AppDownloadInfoDaoImpl extends BaseDaoDB
		implements AppDownloadInfoDao {
}
