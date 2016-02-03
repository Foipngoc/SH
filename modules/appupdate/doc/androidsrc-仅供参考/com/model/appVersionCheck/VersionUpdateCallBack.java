package com.model.appVersionCheck;

public abstract class VersionUpdateCallBack {
	/**
	 * 检查更新出错,或无此APP的信息时
	 */
	public static final int CHECK_RESULT_ERROR = 0;

	/**
	 * 检查没有发现更新
	 */
	public static final int CHECK_RESULT_NOTING_UPDATE = 1;

	/**
	 * 检查到有更新，且类型为强制更新
	 */
	public static final int CHECK_RESULT_UPDATE_TYPE_POP_FORCE = 2;

	/**
	 * 检查到有更新，且类型为自动弹出更新通知，允许用户选择更新或不更新，可以继续使用。
	 */
	public static final int CHECK_RESULT_UPDATE_TYPE_POP_AUTO = 3;

	/**
	 * 检查到有更新，且类型为不自动弹出更新通知
	 */
	public static final int CHECK_RESULT_UPDATE_TYPE_MANUAL_MANUAL = 4;

	/**
	 * 开始下载
	 */
	public static final int BEGIN_DOWNLOAD = 5;

	/**
	 * 正在下载中
	 */
	public static final int DOWNLOADING = 13;

	/**
	 * 下载成功
	 */
	public static final int DOWNLOAD_SUCCESSFUL = 6;

	/**
	 * 下载失败
	 */
	public static final int DOWNLOAD_FAILED = 7;

	/**
	 * 下载中途取消
	 */
	public static final int DOWNLOAD_CANCEL = 8;

	/**
	 * 退出APP
	 */
	public static final int EXIT_APP = 9;

	/**
	 * 延迟更新
	 */
	public static final int DELAY_UPDATE = 10;

	public abstract void callBack(int status, Object... params);
}
