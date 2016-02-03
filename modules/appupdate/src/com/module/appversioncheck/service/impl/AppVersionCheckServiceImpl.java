package com.module.appversioncheck.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.common.utils.FileMd5;
import com.common.utils.FileUtils;
import com.common.utils.LogUtils;
import com.common.utils.PropertyLoader;
import com.common.utils.barcode.QRCodeUtil;
import com.module.appversioncheck.dao.AppDownloadInfoDao;
import com.module.appversioncheck.dao.AppInfoDao;
import com.module.appversioncheck.dao.AppVersionInfoDao;
import com.module.appversioncheck.model.AppDownloadInfo;
import com.module.appversioncheck.model.AppInfo;
import com.module.appversioncheck.model.AppVersionInfo;
import com.module.appversioncheck.service.AppVersionCheckService;

@Service("appVersionCheckService")
@SuppressWarnings("all")
public class AppVersionCheckServiceImpl extends BaseService implements
        AppVersionCheckService {
    private Logger logger = LogUtils
            .getLogger(AppVersionCheckServiceImpl.class);

    private String base_folder = null; // 更新模块数据路径

    @Resource
    private AppDownloadInfoDao appDownloadInfoDao;

    @Resource
    private AppInfoDao appInfoDao;

    @Resource
    private AppVersionInfoDao appVersionInfoDao;

    private String getDefaultBase_folder() {
        return getContextPath() + "/data/appversioncheck";
    }

    private String getRes_folder() {
        if (base_folder == null || base_folder.equals("")) {
            base_folder = getDefaultBase_folder();
        }

        FileUtils.mkdir(base_folder + "/res");
        return base_folder + "/res";
    }

    private String getLogo_folder() {
        if (base_folder == null || base_folder.equals("")) {
            base_folder = getDefaultBase_folder();
        }
        FileUtils.mkdir(base_folder + "/logo");
        return base_folder + "/logo";
    }

    private String getBarcode_folder() {
        if (base_folder == null || base_folder.equals("")) {
            base_folder = getDefaultBase_folder();
        }
        FileUtils.mkdir(base_folder + "/barcode");
        return base_folder + "/barcode";
    }

    @PostConstruct
    public void init() {
        Properties properties = PropertyLoader.getPropertiesFromClassPath(
                "appversioncheck.properties", "UTF-8");

        if (properties != null) {
            String folder = properties.getProperty("base_folder");
            if (folder != null && !folder.equals("")) {
                base_folder = folder;
            }
        }
        logger.debug("APP更新模块数据地址为" + base_folder);
    }

    /**
     * 获得所有已发布的应用信息
     *
     * @return
     */
    public List<Map<String, Object>> queryApps() {
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        List<AppInfo> appInfos = (List<AppInfo>) this.appInfoDao.findOrderBy(
                new AppInfo(), "createdate", true).getData();

        for (int i = 0; i < appInfos.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("appinfo", appInfos.get(i));
            int newestversionid = appInfos.get(i).getNewestappvid();

            if (newestversionid != -1) {
                AppVersionInfo appVersionInfo = this
                        .queryAppVersion(newestversionid);
                if (appVersionInfo != null)
                    map.put("newestvcode", appVersionInfo.getVersionname());
                else
                    map.put("newestvcode", "");
            } else
                map.put("newestvcode", "");
            ret.add(map);
        }

        return ret;
    }

    @Override
    public Map<String, Object> queryAppDetail(int appid) {
        Map<String, Object> map = new HashMap<>();
        AppInfo appInfo = this.queryApp(appid);
        if (appInfo != null) {
            map.put("appinfo", appInfo);
            int newestversionid = appInfo.getNewestappvid();
            if (newestversionid != -1) {
                AppVersionInfo appVersionInfo = this
                        .queryAppVersion(newestversionid);
                if (appVersionInfo != null)
                    map.put("newestvcode", appVersionInfo.getVersionname());
                else
                    map.put("newestvcode", "");
            } else
                map.put("newestvcode", "");
        }
        return map;
    }

    /**
     * 获取某一已发布的应用信息
     *
     * @param appid
     * @return
     */
    public AppInfo queryApp(int appid) {
        return (AppInfo) this.appInfoDao.findUnique(new AppInfo(), "id", appid);
    }

    /**
     * 检查应用名是否存在
     *
     * @param appname
     * @return
     */
    private boolean checkAppName(String appname) {
        // 检查应用名是否重复
        List<AppInfo> appInfos = (List<AppInfo>) this.appInfoDao.find(
                new AppInfo(), "appname", appname).getData();

        if (appInfos != null && appInfos.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 发布新的应用
     *
     * @param appVersionCheckInfo
     */
    public BaseResult publishApp(AppInfo appInfo, File logo, String logoname) {
        // 检查应用名是否重复
        if (checkAppName(appInfo.getAppname())) {
            return new BaseResult(1, "应用名已存在");
        }

        appInfo.setCreatedate(DateTimeUtil.getCurrDate());
        appInfo.setNewestappvid(-1);

        if (logo != null && logoname != null) {
            String logomd5 = FileMd5.getMd5ByFile(logo);

            logoname = "logo" + appInfo.getId() + "."
                    + FileUtils.getFileExtension(logoname);
            logoname = FileUtils.writeToFile(logo, logomd5, getLogo_folder()
                    + "/" + logoname);
            if (logoname == null)
                return new BaseResult(4, "LOGO保存失败");

            appInfo.setApplogomd5(logomd5);
            appInfo.setApplogo(logoname);
        }

        this.appInfoDao.save(appInfo);
        BaseResult result = new BaseResult(0, "发布成功");
        return result;
    }

    /**
     * 删除已发布的应用
     *
     * @param appid
     */
    public void delApp(int appid) {
        AppInfo appInfo = this.queryApp(appid);
        if (appInfo != null) {
            List<AppVersionInfo> appVersionInfos = this.queryAppVersions(appid);
            for (int i = 0; i < appVersionInfos.size(); i++) {
                // 删除应用的所有版本
                this.deleteAppVersion(appVersionInfos.get(i).getId());
            }
            // 刪除该应用
            this.appInfoDao.delete(appInfo);
        }
    }

    /**
     * 更新已发布应用
     *
     * @param appInfo
     */
    public BaseResult updateApp(int appid, String appname, String appdesc,
                                String weixinpage, File logo, String logoname) {

        AppInfo appInfo = this.queryApp(appid);

        if (appInfo == null)
            return new BaseResult(2, "未找到应用");

        if (appname != null && !appname.equals(appInfo.getAppname())) {
            if (checkAppName(appname)) {
                return new BaseResult(1, "应用名已存在");
            }
            appInfo.setAppname(appname);
        }
        if (weixinpage != null && !weixinpage.equals("")) {
            appInfo.setWeixindlpg(weixinpage);
        } else {
            appInfo.setWeixindlpg(null);
        }

        if (appdesc != null) {
            appInfo.setAppdesc(appdesc);
        }

        if (logo != null && logoname != null) {
            String logomd5 = FileMd5.getMd5ByFile(logo);
            logoname = "logo" + appInfo.getId() + "."
                    + FileUtils.getFileExtension(logoname);

            logoname = FileUtils.writeToFile(logo, logomd5, getLogo_folder()
                    + "/" + logoname);
            if (logoname == null)
                return new BaseResult(4, "LOGO保存失败");
            appInfo.setApplogomd5(logomd5);
            appInfo.setApplogo(logoname);
        }

        this.appInfoDao.update(appInfo);
        BaseResult result = new BaseResult(0, "更新成功");
        return result;
    }

    // /////////////////////////////////////////////////////////////////////////////////

    /**
     * 发布应用新版本
     *
     * @param appid
     * @param appVersionInfo
     * @return
     */
    public BaseResult publishAppVersion(int appid, boolean autogenvcode,
                                        int versioncode, String versionname, String updatelog,
                                        int updatetype, boolean autoset, String downloadpath,
                                        int autoinstall, File file, String filename) {

        if (updatetype != AppVersionInfo.UPDATE_TYPE_MANUAL_MANUAL
                && updatetype != AppVersionInfo.UPDATE_TYPE_POP_AUTO
                && updatetype != AppVersionInfo.UPDATE_TYPE_POP_FORCE)
            return new BaseResult(-1, "参数错误");

        AppInfo appInfo = this.queryApp(appid);

        if (appInfo == null)
            return new BaseResult(3, "未找到应用");

        int maxvcode = this.appVersionInfoDao.getMaxVersionCode(appid);

        // 自动生成版本号
        if (autogenvcode) {
            versioncode = maxvcode + 1;
        } else {
            // 版本号必须比上一次发布的版本号增大
            if (versioncode <= maxvcode)
                return new BaseResult(1, "版本号必须大于当前发布版本中最大版本号：" + maxvcode);
        }
        if (file == null) {
            return new BaseResult(2, "APP上传失败");
        }

        String apkmd5 = FileMd5.getMd5ByFile(file);
        if (apkmd5 == null || apkmd5.equals("")) {
            return new BaseResult(5, "MD5校验失败");
        }

        // 获得res目录 ，如果不存在，创建
        FileUtils.mkdir(getRes_folder() + "/" + appid);

        // 获得res文件路径，如果没有filename，默认为app.dl,如果文件已存在，则删除之
        if (filename == null || filename.equals(""))
            filename = "app.dl";

        filename = FileUtils.writeToFile(file, apkmd5, getRes_folder() + "/"
                + appid + "/" + filename);
        if (filename == null)
            return new BaseResult(4, "文件保存失败");

        AppVersionInfo appVersionInfo = new AppVersionInfo();

        appVersionInfo.setAppid(appid);
        appVersionInfo.setRespath(filename);
        appVersionInfo.setResmd5(apkmd5);
        appVersionInfo.setUpdatelog(updatelog);
        appVersionInfo.setUpdatetype(updatetype);
        appVersionInfo.setVersioncode(versioncode);
        appVersionInfo.setVersionname(versionname);
        appVersionInfo.setUpdatedate(DateTimeUtil.getCurrDate());
        appVersionInfo.setDownloadpath(downloadpath);
        appVersionInfo.setAutoinstall(autoinstall);
        this.appVersionInfoDao.save(appVersionInfo);

        if (autoset) {
            // 更新应用最新版为此次发布 AppInfo appInfo = this.queryApp(appid);
            appInfo.setNewestappvid(appVersionInfo.getId());
            this.appInfoDao.update(appInfo);
        }

        return new BaseResult(0, "发布成功");

    }

    /**
     * 设置APP最新的版本为哪个
     *
     * @param appid
     * @param appvid
     * @return
     */
    public BaseResult setNewestAppVersion(int appid, int appvid) {
        AppInfo appInfo = this.queryApp(appid);
        if (appInfo == null)
            return new BaseResult(1, "应用不存在");

        AppVersionInfo appVersionInfo = this.queryAppVersion(appvid);
        if (appVersionInfo.getAppid() != appid)
            return new BaseResult(2, "应用不存在该版本");
        appInfo.setNewestappvid(appvid);

        this.appInfoDao.update(appInfo);

        return new BaseResult(0, "设置成功");
    }

    /**
     * 更新应用已发布版本信息
     *
     * @param appVersionInfo
     */
    public BaseResult updateAppVersion(int appvid, String versionname,
                                       String updatelog, int updatetype, String downloadpath,
                                       int autoinstall, File file, String filename) {
        AppVersionInfo appVersionInfodb = this.queryAppVersion(appvid);
        if (appVersionInfodb == null)
            return new BaseResult(1, "版本不存在");

        if (versionname != null && !versionname.equals(""))
            appVersionInfodb.setVersionname(versionname);

        if (updatelog != null && !updatelog.equals(""))
            appVersionInfodb.setUpdatelog(updatelog);

        if (updatetype == AppVersionInfo.UPDATE_TYPE_MANUAL_MANUAL
                || updatetype == AppVersionInfo.UPDATE_TYPE_POP_AUTO
                || updatetype == AppVersionInfo.UPDATE_TYPE_POP_FORCE)
            appVersionInfodb.setUpdatetype(updatetype);

        if (downloadpath != null && !downloadpath.equals(""))
            appVersionInfodb.setDownloadpath(downloadpath);

        if (autoinstall != -1
                && (autoinstall == AppVersionInfo.AUTOINSTALL_YES || autoinstall == AppVersionInfo.AUTOINSTALL_NO))
            appVersionInfodb.setAutoinstall(autoinstall);

        if (file != null) {
            String apkmd5 = FileMd5.getMd5ByFile(file);
            if (apkmd5 == null || apkmd5.equals("")) {
                return new BaseResult(5, "MD5校验失败");
            }

            if (filename == null || filename.equals(""))
                filename = filename + "/app.dl";

            filename = FileUtils.writeToFile(file, apkmd5, getRes_folder()
                    + "/" + appVersionInfodb.getAppid() + "/" + filename);
            if (filename == null)
                return new BaseResult(4, "文件保存失败");

            appVersionInfodb.setResmd5(apkmd5);
            appVersionInfodb.setRespath(filename);
        }

        this.appVersionInfoDao.update(appVersionInfodb);
        return new BaseResult(0, "更新成功");
    }

    /**
     * 删除应用已发布的版本, 注： 如果当前用户已安装了被删除的版本，则用户更新时会提醒强制更新
     *
     * @param appvid
     */
    public void deleteAppVersion(int appvid) {
        AppVersionInfo appVersionInfo = this.queryAppVersion(appvid);
        if (appVersionInfo != null) {
            AppInfo appInfo = this.queryApp(appVersionInfo.getAppid());
            if (appInfo != null) {
                // 如果删除的版本为当前最新的版本，将最新版本往前移一个版本，如果前一个版本没有，则尝试往后一个版本，不成功则设置为-1
                if (appInfo.getNewestappvid() == appVersionInfo.getId()) {
                    AppVersionInfo appVersionInfoBefore = queryBeforeAppVersion(
                            appVersionInfo.getAppid(), appvid);
                    if (appVersionInfoBefore != null)
                        appInfo.setNewestappvid(appVersionInfoBefore.getId());
                    else {
                        AppVersionInfo appVersionInfoAfter = queryAfterAppVersion(
                                appVersionInfo.getAppid(), appvid);
                        if (appVersionInfoAfter != null)
                            appInfo.setNewestappvid(appVersionInfoAfter.getId());
                        else
                            appInfo.setNewestappvid(-1);
                    }
                    this.appInfoDao.update(appInfo);
                }
                this.appVersionInfoDao.delete(appVersionInfo);
            }
        }
    }

    /**
     * 获取某应用发布的所有版本信息
     *
     * @param appid
     * @return
     */
    public List<AppVersionInfo> queryAppVersions(int appid) {
        List<AppVersionInfo> list = (List<AppVersionInfo>) this.appVersionInfoDao
                .findOrderBy(new AppVersionInfo(), "appid", appid,
                        "updatedate", true).getData();
        return list;
    }

    /**
     * 获取某应用的某版本信息
     *
     * @param appid
     * @param appvid
     * @return
     */
    public AppVersionInfo queryAppVersion(int appvid) {
        AppVersionInfo appVersionInfo = (AppVersionInfo) this.appVersionInfoDao
                .findUnique(new AppVersionInfo(), "id", appvid);
        return appVersionInfo;
    }

    /**
     * 通过版本号获取某应用的版本信息
     *
     * @param appid
     * @param vcode
     * @return
     */
    public AppVersionInfo queryAppVersion(int appid, int vcode) {
        AppVersionInfo appVersionInfo = this.appVersionInfoDao
                .queryAppVersionInfo(appid, vcode);
        return appVersionInfo;
    }

    /**
     * 查询appvid的前一版
     *
     * @param appid
     * @param appvid
     * @return
     */
    private AppVersionInfo queryBeforeAppVersion(int appid, int appvid) {
        AppVersionInfo appVersionInfo = null;
        List<AppVersionInfo> appVersionInfos = this.queryAppVersions(appid);
        int i = 0;
        for (; i < appVersionInfos.size(); i++) {
            if (appVersionInfos.get(i).getId() == appvid)
                break;
        }

        if (i > 0)
            return appVersionInfos.get(i - 1);
        else
            return null;
    }

    /**
     * 查询appvid的后一版
     *
     * @param appid
     * @param appvid
     * @return
     */
    private AppVersionInfo queryAfterAppVersion(int appid, int appvid) {
        AppVersionInfo appVersionInfo = null;
        List<AppVersionInfo> appVersionInfos = this.queryAppVersions(appid);
        int i = 0;
        for (; i < appVersionInfos.size(); i++) {
            if (appVersionInfos.get(i).getId() == appvid)
                break;
        }

        if (i < appVersionInfos.size() - 1)
            return appVersionInfos.get(i + 1);
        else
            return null;
    }

    /**
     * 检查新版本信息
     *
     * @param appid
     * @param oldversioncode
     * @return
     */
    public BaseResult checkNewestAppVersion(int appid, int oldversioncode) {
        AppInfo appInfo = this.queryApp(appid);

        if (appInfo == null) {
            return new BaseResult(0, "不存在该应用");
        }

        // 获得最新版本APP
        AppVersionInfo newestAppVersionInfo = null;

        if (appInfo.getNewestappvid() != -1)
            try {
                newestAppVersionInfo = this.queryAppVersion(
                        appInfo.getNewestappvid()).cloneObject();// clone对象，避免hibernate对持久化对象自动保存
            } catch (Exception e) {
                newestAppVersionInfo = null;
            }

        // 如果没有最新版本，则提醒已为最新版本，无需更新
        if (newestAppVersionInfo == null) {
            return new BaseResult(1, "已为最新版本");
        }

        // 如果系统没有oldversioncode这个版本
        AppVersionInfo oldAppVersionInfo = this.queryAppVersion(appid,
                oldversioncode);
        if (oldAppVersionInfo == null) {
            // 如果系统中没有该版本，但是用户的当前版本号>=目前系统的最新版本，则不进行更新， 常出现在debug时
            if (oldversioncode >= newestAppVersionInfo.getVersioncode()) {
                return new BaseResult(1, "已为最新版本");
            } else {
                newestAppVersionInfo
                        .setUpdatetype(AppVersionInfo.UPDATE_TYPE_POP_FORCE);
                BaseResult result = new BaseResult(2, "有新版本可以升级");
                result.addToMap("appversioninfo", newestAppVersionInfo);
                result.addToMap("appinfo", appInfo);
                return result;
            }
        }

        // 如果原版本大于或等于当前最新版本
        if (oldAppVersionInfo.getVersioncode() >= newestAppVersionInfo
                .getVersioncode()) {
            return new BaseResult(1, "已为最新版本");
        }

        /**
         * 寻找在最新版本与old版本之间的所有版本
         */
        List<AppVersionInfo> appVersionInfos = this.appVersionInfoDao
                .queryAppVersionInfos(appid,
                        newestAppVersionInfo.getVersioncode(),
                        oldAppVersionInfo.getVersioncode());

        /**
         * 1. 如果从oldversioncode到最新版本，存在需要强制更新的版本，则返回的版本信息中为必须强制更新 2. 更新日志需要叠加
         */
        String updatelog = newestAppVersionInfo.getVersionname() + "\r\n";
        for (int i = 0; i < appVersionInfos.size(); i++) {
            AppVersionInfo appVersionInfo = appVersionInfos.get(i);

            updatelog += appVersionInfo.getUpdatelog() + "\r\n";

            if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_FORCE) {
                newestAppVersionInfo
                        .setUpdatetype(AppVersionInfo.UPDATE_TYPE_POP_FORCE);
                break;
            }
        }
        newestAppVersionInfo.setUpdatelog(updatelog.toString());

        BaseResult result = new BaseResult(2, "有新版本可以升级");

        result.addToMap("appversioninfo", newestAppVersionInfo);
        result.addToMap("appinfo", appInfo);
        return result;
    }

    /**
     * 下载最新版本
     *
     * @param appid
     * @return
     */
    public File downloadNewestAppVersionRes(int appid, int oldvesioncode,
                                            String imei, String ipaddr, String macaddr, String clientinfo) {

        BaseResult checkResult = this.checkNewestAppVersion(appid,
                oldvesioncode);

        // 先检查下是否允许更新
        if (checkResult.getResultcode() == 2) {
            // 获得最新版本APP
            AppInfo appInfo = this.queryApp(appid);
            AppVersionInfo oldAppVersionInfo = this.queryAppVersion(appid,
                    oldvesioncode);
            AppVersionInfo newestAppVersionInfo = this.queryAppVersion(appInfo
                    .getNewestappvid());

            if (newestAppVersionInfo.getRespath() == null
                    || newestAppVersionInfo.getRespath().equals("")) {
                return null;
            }

            String filename = newestAppVersionInfo.getRespath();
            String filemd5 = FileMd5.getMd5ByFile(getRes_folder() + "/"
                    + appInfo.getId() + "/" + filename);
            if (filemd5 == null
                    || !filemd5.toLowerCase().equals(
                    newestAppVersionInfo.getResmd5().toLowerCase()))
                return null;

            /**
             * 添加下载信息
             */
            AppDownloadInfo appDownloadInfo = new AppDownloadInfo();
            appDownloadInfo.setAppid(appid);
            appDownloadInfo.setAppvid(newestAppVersionInfo.getId());
            if (oldAppVersionInfo != null)
                appDownloadInfo.setOldappvid(oldAppVersionInfo.getId());
            else
                appDownloadInfo.setOldappvid(-1);
            appDownloadInfo.setUpdatedate(new Date());
            if (ipaddr != null && !ipaddr.equals(""))
                appDownloadInfo.setIpaddr(ipaddr);
            if (macaddr != null && !ipaddr.equals(""))
                appDownloadInfo.setMacaddr(macaddr);
            if (imei != null && !imei.equals(""))
                appDownloadInfo.setImei(imei);
            if (clientinfo != null && !clientinfo.equals(""))
                appDownloadInfo.setClientinfo(clientinfo);
            else
                appDownloadInfo.setClientinfo("");
            this.appDownloadInfoDao.save(appDownloadInfo);

            return new File(getRes_folder() + "/" + appInfo.getId() + "/"
                    + filename);
        } else {
            return null;
        }
    }

    /**
     * 下载某版本资源
     *
     * @param appid
     * @return
     */
    public File downloadAppVersionRes(int appvid) {
        AppVersionInfo appVersionInfo = this.queryAppVersion(appvid);
        if (appVersionInfo != null) {
            String filename = appVersionInfo.getRespath();
            String filemd5 = FileMd5.getMd5ByFile(getRes_folder() + "/"
                    + appVersionInfo.getAppid() + "/" + filename);
            if (!filemd5.toLowerCase().equals(
                    appVersionInfo.getResmd5().toLowerCase()))
                return null;
            return new File(getRes_folder() + "/" + appVersionInfo.getAppid()
                    + "/" + filename);
        }
        return null;
    }

    @Override
    public BaseResult genBarCode(int appid, String url) {
        if (url == null)
            return new BaseResult(-1, "URL为空");
        String logopath = null;
        String barcodename = "barcode" + appid + ".jpg";
        String barcodepath = getBarcode_folder() + "/" + barcodename;
        AppInfo appInfo = this.queryApp(appid);

        if (appInfo == null) {
            return new BaseResult(-1, "应用不存在");
        }
        logopath = getLogo_folder() + "/" + appInfo.getApplogo();

        if (!FileUtils.checkFileMd5(logopath, appInfo.getApplogomd5())) {
            logopath = null;
        }

        if (logopath == null) {
            new File(barcodepath).delete();
            try {
                QRCodeUtil.encode(url, barcodepath, true);
            } catch (Exception e) {
                return new BaseResult(-1, "生成二维码失败");
            }
        } else {
            try {
                QRCodeUtil.encode(url, logopath, barcodepath, true);
            } catch (Exception e) {
                return new BaseResult(-1, "生成二维码失败");
            }
        }
        appInfo.setBarcode(barcodename);
        appInfo.setBarcodemd5(FileMd5.getMd5ByFile(barcodepath));
        return new BaseResult(0, "生成二维码成功");
    }

    @Override
    public File downloadAppLogo(int appid) {
        String logopath = null;

        AppInfo appInfo = this.queryApp(appid);

        if (appInfo == null) {
            return null;
        }
        logopath = getLogo_folder() + "/" + appInfo.getApplogo();

        if (!FileUtils.checkFileMd5(logopath, appInfo.getApplogomd5())) {
            return null;
        }
        return new File(logopath);
    }

    @Override
    public File downloadBarCode(int appid) {
        AppInfo appInfo = this.queryApp(appid);

        if (appInfo == null) {
            return null;
        }
        String barcodename = appInfo.getBarcode();
        String barcodepath = getBarcode_folder() + "/" + barcodename;

        return new File(barcodepath);
    }
}
