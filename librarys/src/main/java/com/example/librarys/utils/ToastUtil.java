package com.example.librarys.utils;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.librarys.R;

public class ToastUtil {

    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    private static Toast mToast;

    private static Handler mhandler = new Handler();

    private static Runnable r = new Runnable() {

        public void run() {
            mToast.cancel();
        }
    };

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, String message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }


    public static void showToast(Context context, String text, int duration) {
        initToast(context, text, duration);
    }

    public static void showToast(Context context, int text, int duration) {
        initToast(context, context.getString(text), duration);
    }

    private static void initToast(Context context, String text, int duration){
        mhandler.removeCallbacks(r);
        if (null == mToast) {
            mToast = new Toast(context);
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_toast_message, null);
        ViewUtils.updateTextView(view, R.id.id_textView_toastMessage, text);
        mToast.setView(view);
        mToast.setDuration(duration);
        mhandler.postDelayed(r, 5000);
        mToast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, String message, int duration) {
        showToast(context, message, duration);
    }
}
