package com.zhangsunyucong.chanxa.testproject.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.librarys.utils.ValidateUtils;

/**
 * Created by hyj on 2017/8/23 0023.
 */

public class AlertDialogUtils {

    public interface OnPositiveClickListener {
        void onPositiveClick(DialogInterface dialogInterface, int i);
    }

    public interface OnNegativeClickListener {
        void onNegativeClick(DialogInterface dialogInterface, int i);
    }

    public interface OnCancelClickListener {
        void onCancelClick(DialogInterface dialogInterface);
    }

    public static void show(Context context, int title,
                            final OnPositiveClickListener onPositiveClickListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(ValidateUtils.checkNotNULL(onPositiveClickListener)) {
                            onPositiveClickListener.onPositiveClick(dialogInterface, i);
                        }
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                    }
                }).show();
    }

    public static void show(Context context, String title,
                            final OnPositiveClickListener onPositiveClickListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(ValidateUtils.checkNotNULL(onPositiveClickListener)) {
                            onPositiveClickListener.onPositiveClick(dialogInterface, i);
                        }
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                    }
                }).show();
    }
}
