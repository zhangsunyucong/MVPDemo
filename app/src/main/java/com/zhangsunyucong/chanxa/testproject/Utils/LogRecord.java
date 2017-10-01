package com.zhangsunyucong.chanxa.testproject.Utils;

import android.util.Log;

import com.zhangsunyucong.chanxa.testproject.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogRecord {
    private static Boolean MYLOG_SWITCH = true; // 日志文件总开关
    private static Boolean MYLOG_WRITE_TO_FILE = true;// 日志写入文件开关
    private static char MYLOG_TYPE = 'v';// 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
    private static String MYLOG_PATH_SDCARD_DIR = "/sdcard/";// 日志文件在sdcard中的路径
    private static int SDCARD_LOG_FILE_SAVE_DAYS = 0;// sd卡中日志文件的最多保存天数
    private static String MYLOGFILEName = "Log.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");// 日志的输出格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式

    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    private LogRecord() {
        /* Protect from instantiations */
    }

    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    public static void e(String message) {
        if (!isDebuggable())
            return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        log(className, createLog(message), 'e');
    }


    public static void i(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        log(className, createLog(message), 'i');
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        log(className, createLog(message), 'd');
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        log(className, createLog(message), 'v');
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        log(className, createLog(message), 'w');
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag
     * @param msg
     * @param level
     * @return void
     * @since v 1.0
     */
    private static void log(String tag, String msg, char level) {
        if (!isDebuggable())
            return;
        if ('e' == level && ('e' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) { // 输出错误信息
            Log.e(tag, msg);
        } else if ('w' == level && ('w' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Log.w(tag, msg);
        } else if ('d' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Log.d(tag, msg);
        } else if ('i' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Log.i(tag, msg);
        } else {
            Log.v(tag, msg);
        }
        if (MYLOG_WRITE_TO_FILE)
            writeLogtoFile(String.valueOf(level), tag, msg);
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     **/
    private static void writeLogtoFile(String mylogtype, String tag, String text) {// 新建或打开日志文件
        Date nowTime = new Date();
        String needWriteFile = logfile.format(nowTime);
        String needWriteMessage = myLogSdf.format(nowTime) + "    " + mylogtype
                + "    " + tag + "    " + text;
        File file = new File(MYLOG_PATH_SDCARD_DIR, needWriteFile
                + MYLOGFILEName);
        try {
            FileWriter filerWriter = new FileWriter(file, true);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 删除制定的日志文件
     */
    public static void delFile() {// 删除日志文件
        String needDelFile = logfile.format(getDateBefore());
        File file = new File(MYLOG_PATH_SDCARD_DIR, needDelFile + MYLOGFILEName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
     */
    private static Date getDateBefore() {
        Date nowTime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowTime);
        now.set(Calendar.DATE, now.get(Calendar.DATE)
                - SDCARD_LOG_FILE_SAVE_DAYS);
        return now.getTime();
    }
}