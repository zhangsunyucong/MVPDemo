package com.example.librarys.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhangsunyucong on 2016/9/22.
 */

public class ViewUtils {

    public static void updateTextView(View view, int textViewId, int textStringId) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(textViewId);
            if (textView != null) {
                textView.setText(textStringId);
            }
        }
    }

    public static void updateTextView(View view, int textViewId, String textString) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(textViewId);
            if (textView != null) {
                textView.setText(textString);
            }
        }
    }

    public static void updateTextView(View view, int textViewId,
                                      SpannableStringBuilder spannableStringBuilder) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(textViewId);
            if (textView != null) {
                textView.setText(spannableStringBuilder);
            }
        }

    }

    public static void updateTextView(Activity activity, int textViewId,
                                      int textStringId) {
        if (activity != null) {
            TextView textView = (TextView) activity.findViewById(textViewId);
            if (textView != null) {
                textView.setText(textStringId);
            }
        }
    }

    public static void updateTextView(FragmentActivity activity, int textViewId,
                                      String textStringId) {
        if (activity != null) {
            TextView textView = (TextView) activity.findViewById(textViewId);
            if (textView != null) {
                textView.setText(textStringId);
            }
        }
    }

    public static void updateTextView(Activity activity, int textViewId,
                                      String textString) {
        if (activity != null) {
            TextView textView = (TextView) activity.findViewById(textViewId);
            if (textView != null) {
                textView.setText(textString);
            }
        }

    }

    public static void updateEditText(Activity activity, int ediTextId,
                                      String textString) {
        if (activity != null) {
            EditText editText = (EditText) activity.findViewById(ediTextId);
            if (editText != null) {
                editText.setText(textString);
                if(textString != null) {
                    editText.setSelection(textString.length());
                }
            }
        }
    }

    public static void setOnEditorActionListener(EditText editview, TextView.OnEditorActionListener listener) {
        if(editview != null) {
            editview.setOnEditorActionListener(listener);
        }

    }

    public static void addTextChangedListener(EditText editview, TextWatcher listener) {
        if(editview != null) {
            editview.addTextChangedListener(listener);
        }

    }

    public static void updateEditTextColor(Activity activity, int ediTextId, int textColorId) {
        if (activity != null) {
            EditText editText = (EditText) activity.findViewById(ediTextId);
            if (editText != null) {
                editText.setTextColor(activity.getResources().getColor(
                        textColorId));
            }
        }
    }

    public static void updateEditText(AppCompatActivity activity, int layoutId, int ediTextId,
                                      String textString) {
        if (activity != null) {

            View view = activity.findViewById(layoutId);

            if(view != null) {
                EditText editText = (EditText) view.findViewById(ediTextId);
                if (editText != null) {
                    editText.setText(textString);
                    if(textString != null) {
                        editText.setSelection(textString.length());
                    }
                }
            }

        }
    }

    public static void updateEditText(View view, int layoutId, int ediTextId,
                                      String textString) {
        if (view != null) {

            View layout = view.findViewById(layoutId);

            if(layout != null) {

                EditText editText = (EditText) layout.findViewById(ediTextId);
                if (editText != null) {
                    editText.setText(textString);
                    if(textString != null) {
                        editText.setSelection(textString.length());
                    }
                }
            }

        }
    }

    public static void updateEditText(EditText editText,
                                      String textString) {
        if (editText != null) {
            editText.setText(textString);
            if(textString != null) {
                editText.setSelection(textString.length());
            }
        }
    }

    public static void updateEditText(View view, int ediTextId,
                                      String textString) {
        if (view != null) {
            EditText editText = (EditText) view.findViewById(ediTextId);
            if (editText != null) {
                editText.setText(textString);
                if(textString != null) {
                    editText.setSelection(textString.length());
                }
            }
        }
    }

    public static void updateEditHint(AppCompatActivity activity, int ediTextId,
                                      String textString) {
        if(activity != null) {
            EditText editText = (EditText) activity.findViewById(ediTextId);
            if (editText != null) {
                editText.setHint(textString);
            }
        }
    }

    public static void updateEditHint(AppCompatActivity activity, int ediTextId,
                                      int textStringId) {
        if(activity != null) {
            EditText editText = (EditText) activity.findViewById(ediTextId);
            if (editText != null) {
                editText.setHint(textStringId);
            }
        }
    }

    public static void updateEditHint(Activity activity, int ediTextId,
                                      String textString) {
        if(activity != null) {
            EditText editText = (EditText) activity.findViewById(ediTextId);
            if (editText != null) {
                editText.setHint(textString);
            }
        }
    }

    public static void updateEditHint(View view, int ediTextId,
                                      String textString) {
        if (view != null) {
            EditText editText = (EditText) view.findViewById(ediTextId);
            if (editText != null) {
                editText.setHint(textString);
            }
        }
    }

    public static void updateEditHint(View view, int ediTextId,
                                      int textString) {
        if (view != null) {
            EditText editText = (EditText) view.findViewById(ediTextId);
            if (editText != null) {
                editText.setHint(textString);
            }
        }
    }

    public static void updateEditHint(AppCompatActivity activity, int srcViewId,
                                      int ediTextId, int textString) {
        if(activity != null) {
            View view = activity.findViewById(srcViewId);
            if(view != null) {
                EditText editText = (EditText) view.findViewById(ediTextId);
                if (editText != null) {
                    editText.setHint(textString);
                }
            }
        }
    }

    public static void updateButtonText(Activity activity, int btnId,
                                        String textString){
        if(activity != null){
            Button btn = (Button) activity.findViewById(btnId);
            if (btn != null) {
                btn.setText(textString);
            }
        }
    }

    public static void setViewVisibility(Activity activity, int layoutId, int viewId, int visibility) {

        if (activity != null) {
            View layout = activity.findViewById(layoutId);
            if(layout != null) {
                View dstView = layout.findViewById(viewId);
                if(dstView != null) {
                    dstView.setVisibility(visibility);
                }
            }
        }

    }

    public static void setViewVisibility(View view, int visibility) {

        if (view != null) {
            view.setVisibility(visibility);
        }

    }

    public static void setViewVisibility(View dstView, int dstId, int visibility) {
        if (dstView != null) {
            View view = dstView.findViewById(dstId);

            if (view != null) {
                setViewVisibility(view, visibility);
            }
        }

    }

    public static void setViewVisibility(AppCompatActivity activity, int dstViewId,
                                         int dstId, int visibility){
        View parentView = activity.findViewById(dstViewId);

        if (parentView != null) {
            View view = parentView.findViewById(dstId);
            if(view != null){
                view.setVisibility(visibility);
            }
        }
    }

    public static void setViewVisibility(View view, int dstViewId,
                                         int dstId, int visibility){
        View parentView = view.findViewById(dstViewId);

        if (parentView != null) {
            View dstView = parentView.findViewById(dstId);
            if(dstView != null){
                dstView.setVisibility(visibility);
            }
        }
    }

    public static void setViewVisibility(Activity activity, int dstId,
                                         int visibility) {
        if (activity != null) {
            View view = activity.findViewById(dstId);

            if (view != null) {
                view.setVisibility(visibility);
            }
        }

    }

    public static int getViewVisibility(View dstView,  int dstId){
        if (dstView != null) {
            View view = dstView.findViewById(dstId);

            if (view != null) {
                return view.getVisibility();
            }
        }
        return -1;
    }

    public static int getViewVisibility(Activity activity,  int dstId){
        if (activity != null) {
            View view = activity.findViewById(dstId);

            if (view != null) {
                return view.getVisibility();
            }
        }
        return -1;
    }

    public static void updateTextViewColor(View view, int viewId, int colorId) {
        if (view != null) {
            TextView textViewId = (TextView) view.findViewById(viewId);
            if (textViewId != null) {
                textViewId.setTextColor(view.getResources().getColor(colorId));
            }
        }

    }

    public static String getEditTextString(Activity activity, int dstId) {
        if (activity != null) {
            EditText editText = (EditText) activity.findViewById(dstId);
            if (editText != null) {
                return editText.getText().toString();
            }
        }
        return null;
    }

    public static String getEditTextString(Activity activity, int srcId, int dstId) {
        if (activity != null) {
            View view = activity.findViewById(srcId);
            if(view != null) {
                EditText editText = (EditText) view.findViewById(dstId);
                if (editText != null) {
                    return editText.getText().toString();
                }
            }
        }
        return null;
    }

    public static String getEditTextString(View view, int srcId, int dstId) {
        if (view != null) {
            View srcView = view.findViewById(srcId);
            if(srcView != null) {
                EditText editText = (EditText) srcView.findViewById(dstId);
                if (editText != null) {
                    return editText.getText().toString();
                }
            }
        }
        return null;
    }

    public static String getEditTextString(EditText editView) {
        if(editView != null) {
            return editView.getText().toString();
        }
        return null;
    }

    public static String getEditTextString(View view, int dstId) {
        if (view != null) {
            EditText editText = (EditText) view.findViewById(dstId);
            if (editText != null) {
                return editText.getText().toString();
            }
        }
        return null;
    }

    public static String getTextViewString(View view, int dstId) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(dstId);
            if (textView != null) {
                return textView.getText().toString();
            }
        }
        return null;

    }

    public static String getTextViewString(Activity activity, int dstId) {
        if (activity != null) {
            TextView textView = (TextView) activity.findViewById(dstId);
            if (textView != null) {
                return textView.getText().toString();
            }
        }
        return null;

    }

    public static void setBackgroundColor(View view,int childViewId,int color){
        if (view != null) {
            View childView = view.findViewById(childViewId);
            if (childView != null) {
                childView.setBackgroundColor(color);
            }
        }
    }

    public static void setBackgroundColor(Activity activity,int childViewId,int color){
        if (activity != null) {
            View childView = activity.findViewById(childViewId);
            if (childView != null) {
                childView.setBackgroundColor(color);
            }
        }
    }

    public static void setBackgroundDrawable(View view, int childViewId,
                                             int drawableId) {

        if (view != null) {
            View childView = view.findViewById(childViewId);
            if (childView != null) {
                childView.setBackgroundResource(drawableId);
            }
        }
    }

    public static void setBackgroundDrawable(Activity activity, int layoutId, int childViewId,
                                             int drawableId) {
        if (activity != null) {
            View layout = activity.findViewById(layoutId);
            if(layout != null) {
                View childView = layout.findViewById(layoutId);
                if (childView != null) {
                    childView.setBackgroundResource(drawableId);
                }
            }
        }
    }

    public static void setBackground(Activity activity, int layoutId, int childViewId,
                                             int drawableId) {
        if (activity != null) {
            View layout = activity.findViewById(layoutId);
            if(layout != null) {
                View childView = layout.findViewById(layoutId);
                if (childView != null) {
                    childView.setBackground(activity.getResources().getDrawable(drawableId));
                }
            }
        }
    }

    public static void setBackground(Context activity, View view, int viewId,
                                     int drawableId) {
        if (view != null) {
            View layout = view.findViewById(viewId);
            if(layout != null) {
                if(drawableId == 0) {
                    layout.setBackgroundResource (0);
                } else {
                    layout.setBackground(activity
                            .getResources().getDrawable(drawableId));
                }

            }
        }
    }

    public static void setBackgroundDrawable(Activity activity,
                                             int childViewId, int drawableId) {

        if (activity != null) {
            View childView = activity.findViewById(childViewId);

            if (childView != null) {
                childView.setBackgroundResource(drawableId);
            }
        }
    }

    public static void setImageViewDrawable(Activity activity, int childViewId,
                                            int drawableId) {

        if (activity != null) {
            ImageView childView = (ImageView) activity
                    .findViewById(childViewId);
            if (childView != null) {
                childView.setImageResource(drawableId);
            }
        }
    }

    public static void setImageViewDrawable(Activity activity, int srcViewId, int childViewId,
                                            int drawableId) {

        if (activity != null) {
            View view = activity.findViewById(srcViewId);
            if(view != null) {
                ImageView childView = (ImageView) view
                        .findViewById(childViewId);
                if (childView != null) {
                    childView.setImageResource(drawableId);
                }
            }
        }
    }

    public static void setImageViewDrawable(View view, int childViewId,
                                            int drawableId) {

        if (view != null) {
            ImageView childView = (ImageView) view
                    .findViewById(childViewId);
            if (childView != null) {
                childView.setImageResource(drawableId);
            }
        }
    }

    public static void setTextColor(Activity activity, int viewId, int colorId) {
        if (activity != null) {
            TextView textViewId = (TextView) activity.findViewById(viewId);
            if (textViewId != null) {
                textViewId.setTextColor(activity.getResources().getColor(
                        colorId));
            }
        }

    }

    public static void setTextColor(Context context, View srcView, int viewId, int colorId) {
        if (srcView != null && context != null) {
            TextView textViewId = (TextView) srcView.findViewById(viewId);
            if (textViewId != null) {
                textViewId.setTextColor(context.getResources().getColor(
                        colorId));
            }
        }

    }

    public static void setTextColor(Activity activity, View view, int viewId, int colorId) {
        if (activity != null && view != null) {
            TextView textViewId = (TextView) view.findViewById(viewId);
            if (textViewId != null) {
                textViewId.setTextColor(activity.getResources().getColor(
                        colorId));
            }
        }

    }

    public static void setBtnTextColor(Activity activity, int viewId,
                                       int colorId) {
        if (activity != null) {
            Button button = (Button) activity.findViewById(viewId);
            if (button != null) {
                button.setTextColor(activity.getResources().getColor(colorId));
            }
        }
    }

    public static void setImageButtonImageResource(View view,int imageButtonId,int resourceId){
        if (view!=null) {
            ImageButton imageButton = (ImageButton)view.findViewById(imageButtonId);

            if (imageButton!=null) {
                imageButton.setImageResource(resourceId);
            }
        }
    }
    public static void setButtonClickable(Activity activity, int viewId, boolean clickable){
        Button btn = (Button) activity.findViewById(viewId);
        btn.setClickable(clickable);
    }

    public static void setButtonClickable(View view, int viewId, boolean clickable){
        Button btn = (Button) view.findViewById(viewId);
        btn.setClickable(clickable);
    }

    public static void updateTextViewWithTimeFormat(TextView view, int second) {
        if (view != null) {
            int hh = second / 3600;
            int mm = second % 3600 / 60;
            int ss = second % 60;
            String strTemp = null;
            if (0 != hh) {
                strTemp = String.format("%02d:%02d:%02d", hh, mm, ss);
            } else {
                strTemp = String.format("%02d:%02d", mm, ss);
            }
            view.setText(strTemp);
        }
    }

    public static void setEditViewSelection(Activity activity,
                                            int editviewId, int position) {

        if(activity != null) {
            EditText editText = (EditText) activity.findViewById(editviewId);
            if(editText != null) {
                editText.setSelection(position);
            }
        }

    }

    public static void setEditViewSelection(View view,
                                            int editviewId, int position) {

        if(view != null) {
            EditText editText = (EditText) view.findViewById(editviewId);
            if(editText != null) {
                editText.setSelection(position);
            }
        }

    }

    public static void setEditViewInputType(Activity activity,
                                            int editviewId, int inputType) {
        if(activity != null) {
            EditText editText = (EditText) activity.findViewById(editviewId);
            if(editText != null) {
                editText.setInputType(inputType);
            }
        }
    }

    public static void setEditViewInputType(Activity activity, int layoutId,
                                            int editviewId, int inputType) {
        if(activity != null) {

            View view = activity.findViewById(layoutId);
            if(view != null) {
                EditText editText = (EditText) view.findViewById(editviewId);
                if(editText != null) {
                    editText.setInputType(inputType);
                }
            }
        }
    }

    public static void setEditViewTransformationMethod(Activity activity, int layoutId,
                                                       int editviewId,
                                                       TransformationMethod transformationMethod) {
        if(activity != null && transformationMethod != null) {
            View view = activity.findViewById(layoutId);
            if(view != null) {
                EditText editText = (EditText) view.findViewById(editviewId);
                if(editText != null) {
                    editText.setTransformationMethod(transformationMethod);

                    editText.postInvalidate();
                    CharSequence charSequence = editText.getText();
                    if (charSequence instanceof Spannable) {
                        Spannable spanText = (Spannable) charSequence;
                        Selection.setSelection(spanText, charSequence.length());
                    }
                }
            }
        }
    }

    public static void setEditViewTransformationMethod(View view, int layoutId,
                                                       int editviewId,
                                                       TransformationMethod transformationMethod) {
        if(view != null && transformationMethod != null) {
            View layout = view.findViewById(layoutId);
            if(layout != null) {
                EditText editText = (EditText) layout.findViewById(editviewId);
                if(editText != null) {
                    editText.setTransformationMethod(transformationMethod);

                    editText.postInvalidate();
                    CharSequence charSequence = editText.getText();
                    if (charSequence instanceof Spannable) {
                        Spannable spanText = (Spannable) charSequence;
                        Selection.setSelection(spanText, charSequence.length());
                    }
                }
            }
        }
    }
}
