package com.zhangsunyucong.chanxa.testproject.common;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;

import com.zhangsunyucong.chanxa.testproject.R;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by zhangsunyucong on 2016/10/4.
 */

public class ProgressDialogUtil extends Dialog
{

    private MaterialProgressBar mMaterialProgressBar;
    private boolean mCancelable = true;
    private OnCancelListener mOnCancelListener;

    public ProgressDialogUtil(Context context)
    {
        this(context, R.style.CustomDialog);
    }

    public ProgressDialogUtil(Context context, OnCancelListener cancelListener)
    {
        this(context, R.style.CustomDialog);
        mOnCancelListener = cancelListener;
    }

    public ProgressDialogUtil(Context context, int theme)
    {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_load_dialog);
        mMaterialProgressBar = (MaterialProgressBar) findViewById(R.id.progress);
        ColorStateList csl = ContextCompat.getColorStateList(getContext(),
                R.color.progress_dialog_color);
        mMaterialProgressBar.setProgressTintList(csl);
    }

    private void init(Context context)
    {

        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(mCancelable);
        setCanceledOnTouchOutside(mCancelable);
        setOnCancelListener(mOnCancelListener);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

    }

    @Override
    public void show()
    {
        init(getContext());
        super.show();
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void setCancelable(boolean cancelEnable){
        mCancelable = cancelEnable;
    }
}