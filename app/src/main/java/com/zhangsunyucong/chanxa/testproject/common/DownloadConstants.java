package com.zhangsunyucong.chanxa.testproject.common;

import android.os.Environment;

/**
 * Created by hyj on 2017/8/14 0014.
 */

public class DownloadConstants {
    public static final String EXTERNAL_STORAGE_DIRECTORY_PATH = Environment.getExternalStorageDirectory().getPath();
    public static final String DELETE_ALL_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/jiangebulou/";
    public static final String IMAGIE_PATH = EXTERNAL_STORAGE_DIRECTORY_PATH + "/jiangebulou/images/";
    public static final String BROWSER_DOWNLOAD_PATH = EXTERNAL_STORAGE_DIRECTORY_PATH + "/jiangebulou/browser/download/";
    public static final String APK_PATH = EXTERNAL_STORAGE_DIRECTORY_PATH + "/jiangebulou/apk/";
}
