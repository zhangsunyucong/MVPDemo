package com.zhangsunyucong.chanxa.testproject.manager;

import android.content.Context;
import android.text.TextUtils;

import com.example.librarys.rxdownload2.RxDownload;
import com.example.librarys.rxdownload2.entity.DownloadStatus;
import com.example.librarys.utils.FileUtils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hyj on 2017/8/14 0014.
 */

public class DownloadManager {

    private static DownloadManager mDownloadManager;

    private DownloadManager() {}

    public static DownloadManager getInstance() {
        if(mDownloadManager == null) {
            synchronized (DownloadManager.class) {
                if(mDownloadManager == null) {
                    mDownloadManager = new DownloadManager();
                }
            }
        }
        return mDownloadManager;
    }

    public boolean checkFileIfExist(Context context,String url) {
        if(TextUtils.isEmpty(url)) {
            return false;
        }
        File[] files = RxDownload.getInstance(context).getRealFiles(url);
        File file = null;
        if (files != null) {
            file = files[0];
        }

        return FileUtils.isFileExists(file);
    }

    public File getFileByUrl(Context context, String url) {

        if (TextUtils.isEmpty(url)) {
            return null;
        }

        if(!checkFileIfExist(context, url)) {
            return null;
        }
        File[] files = RxDownload.getInstance(context).getRealFiles(url);
        File file = null;
        if (files != null) {
            file = files[0];
        }
        return file;
    }

    public Observable<DownloadStatus> downFileByUrl(
            final Context context,
            String defaultSavePath,
            String url) {

        if(checkFileIfExist(context, url)) {
            return null;
        }

        return RxDownload.getInstance(context)
                .defaultSavePath(defaultSavePath)
                .download(url)                       //只传url即可
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
