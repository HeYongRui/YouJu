package com.heyongrui.base.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.heyongrui.base.R;
import com.heyongrui.base.widget.MaxHeightScrollView;

/**
 * Created by lambert on 2017/6/29.
 * 对话框工具
 */

public class DialogUtil {

    /**
     * 权限提示框
     */
    public static void showPermissionDialog(Context context, CharSequence permissionTip) {
        if (context == null) return;
        new DialogUtil.DialogBuilder(context).setTitle(context.getString(R.string.permisson_title)).setContent(permissionTip)
                .setConfirmText(context.getString(R.string.go_setting)).setCancelText(context.getString(R.string.cancel)).setContentGravity(Gravity.CENTER_VERTICAL)
                .setDialogClickCallback(new DialogUtil.DialogClickCallback() {
                    @Override
                    public void onYes(Dialog dialog) {
                        dialog.dismiss();
                        //去权限设置页面
                        PermissionPageUtils permissionPageUtils = new PermissionPageUtils(context);
                        permissionPageUtils.jumpPermissionPage();
                    }

                    @Override
                    public void onNo(Dialog dialog) {
                        dialog.dismiss();
                    }
                }).showRemindDialog();
    }

    /**
     * 创建一个加载对话框
     */
    public static Dialog creatLoadingDialog(Context context, @DrawableRes int gif_res, CharSequence loading_text) {
        if (context == null) return null;
        Dialog loadingDialog = new Dialog(context, R.style.Dialog);
        //自定义弹窗根视图
        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        constraintLayout.setPadding(ConvertUtils.dp2px(10), ConvertUtils.dp2px(10), ConvertUtils.dp2px(10), ConvertUtils.dp2px(10));
        GradientDrawable gradientDrawable = new DrawableUtil.DrawableBuilder(context)
                .setGradientRoundRadius(ConvertUtils.dp2px(18))
                .setColor(Color.WHITE).createGradientDrawable();
        ViewCompat.setBackground(constraintLayout, gradientDrawable);
        //自定义加载动画
        ImageView loadingIv = new ImageView(context);
        loadingIv.setId(R.id.load_iv);
        loadingIv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        GlideUtil.loadGif(context, gif_res, loadingIv, 0, null, null);
        constraintLayout.addView(loadingIv);
        //自定义提示文本
        TextView loadingTv = new TextView(context);
        loadingTv.setId(R.id.tv_content);
        loadingTv.setGravity(Gravity.CENTER);
        loadingTv.setSingleLine(true);
        loadingTv.setEllipsize(TextUtils.TruncateAt.END);
        loadingTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        loadingTv.setText(TextUtils.isEmpty(loading_text) ? "" : loading_text);
        loadingTv.setTextColor(ContextCompat.getColor(context, R.color.text_color));
        constraintLayout.addView(loadingTv);
        //对自定义视图的位置动态调整
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.constrainWidth(loadingIv.getId(), ConvertUtils.dp2px(80));
        constraintSet.constrainHeight(loadingIv.getId(), ConvertUtils.dp2px(80));
        constraintSet.connect(loadingIv.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(loadingIv.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(loadingIv.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(loadingTv.getId(), ConstraintSet.TOP, loadingIv.getId(), ConstraintSet.BOTTOM, ConvertUtils.dp2px(10));
        constraintSet.connect(loadingTv.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(loadingTv.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(loadingTv.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        if (!TextUtils.isEmpty(loading_text)) {
            //如果加载提示文字不为空，就创建加载文本控件
            constraintSet.setVisibility(loadingTv.getId(), ConstraintSet.VISIBLE);
        } else {
            constraintSet.setVisibility(loadingTv.getId(), ConstraintSet.GONE);
        }
        constraintSet.applyTo(constraintLayout);
        //设置加载窗口自定义视图和属性
        loadingDialog.setContentView(constraintLayout);
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        //动态设置加载窗口的宽高一致
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        constraintLayout.measure(0, h);
        int height = constraintLayout.getMeasuredHeight();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(height, height);
        constraintLayout.setLayoutParams(layoutParams);
        return loadingDialog;
    }

    public static class DialogBuilder {
        private Context mContext;

        //remindDialog参数
        private CharSequence mTitle;
        private CharSequence mContent;
        private CharSequence mConfirmText;
        private CharSequence mCancelText;
        private boolean mIsShowConfirm = true;
        private boolean mIsShowCancel = true;
        private int mContentGravity = Gravity.CENTER;
        private boolean mCancelable = true;
        private boolean mOutsideCancelable = false;
        private DialogClickCallback mDialogClickCallback;

        public DialogBuilder(Context context) {
            mContext = context;
        }

        public DialogBuilder setTitle(CharSequence title) {
            this.mTitle = title;
            return this;
        }

        public DialogBuilder setContent(CharSequence content) {
            this.mContent = content;
            return this;
        }

        public DialogBuilder setConfirmText(CharSequence confirmText) {
            this.mConfirmText = confirmText;
            return this;
        }

        public DialogBuilder setCancelText(CharSequence cancelText) {
            this.mCancelText = cancelText;
            return this;
        }

        public DialogBuilder setContentGravity(int contentGravity) {
            this.mContentGravity = contentGravity;
            return this;
        }

        public DialogBuilder setIsShowConfirm(boolean isShowConfirm) {
            this.mIsShowConfirm = isShowConfirm;
            return this;
        }

        public DialogBuilder setIsShowCancel(boolean isShowCancel) {
            this.mIsShowCancel = isShowCancel;
            return this;
        }

        public DialogBuilder setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public DialogBuilder setOutsideCancelable(boolean outsideCancelable) {
            this.mOutsideCancelable = outsideCancelable;
            return this;
        }

        public DialogBuilder setDialogClickCallback(DialogClickCallback dialogClickCallback) {
            this.mDialogClickCallback = dialogClickCallback;
            return this;
        }

        public Dialog showRemindDialog() {
            if (mContext == null) return null;
            int roundRadius = 20;
            Dialog remindDialog = new Dialog(mContext, R.style.Dialog);
            View customView = LayoutInflater.from(mContext).inflate(R.layout.dialog_layout_reminder, null);
            GradientDrawable roundDrawable = new DrawableUtil.DrawableBuilder(mContext).setColor(Color.WHITE).setGradientRoundRadius(roundRadius).createGradientDrawable();
            ViewCompat.setBackground(customView, roundDrawable);
            TextView tvTitle = customView.findViewById(R.id.tv_title);
            MaxHeightScrollView maxHeightScrollView = customView.findViewById(R.id.scroll_view);
            maxHeightScrollView.setMaxHeight((int) (ScreenUtils.getScreenHeight() * 0.6));
            TextView tvContent = customView.findViewById(R.id.tv_content);
            TextView tvConfirm = customView.findViewById(R.id.tv_confirm);
            TextView tvCancel = customView.findViewById(R.id.tv_cancel);
            if (!TextUtils.isEmpty(mTitle)) {
                tvTitle.setText(mTitle);
            }
            tvContent.setGravity(mContentGravity);
            tvContent.setText(TextUtils.isEmpty(mContent) ? "" : mContent);
            if (!TextUtils.isEmpty(mConfirmText)) {
                tvConfirm.setText(mConfirmText);
            }
            if (!TextUtils.isEmpty(mCancelText)) {
                tvCancel.setText(mCancelText);
            }
            DrawableUtil.DrawableBuilder confirmBuilder = new DrawableUtil.DrawableBuilder(mContext).setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            DrawableUtil.DrawableBuilder cancelBuilder = new DrawableUtil.DrawableBuilder(mContext).setColor(ContextCompat.getColor(mContext, R.color.window_background));
            if (mIsShowConfirm) {
                confirmBuilder.setGradientRBRoundRadius(roundRadius);
                tvConfirm.setVisibility(View.VISIBLE);
            } else {
                cancelBuilder.setGradientRBRoundRadius(roundRadius);
                tvConfirm.setVisibility(View.GONE);
            }
            if (mIsShowCancel) {
                cancelBuilder.setGradientLBRoundRadius(roundRadius);
                tvCancel.setVisibility(View.VISIBLE);
            } else {
                confirmBuilder.setGradientLBRoundRadius(roundRadius);
                tvCancel.setVisibility(View.GONE);
            }
            if (tvConfirm.getVisibility() == View.VISIBLE) {
                ViewCompat.setBackground(tvConfirm, confirmBuilder.createGradientDrawable());
            }
            if (tvCancel.getVisibility() == View.VISIBLE) {
                ViewCompat.setBackground(tvCancel, cancelBuilder.createGradientDrawable());
            }
            tvConfirm.setOnClickListener(view -> {
                if (mDialogClickCallback != null) {
                    mDialogClickCallback.onYes(remindDialog);
                } else {
                    remindDialog.dismiss();
                }
            });
            tvCancel.setOnClickListener(view -> {
                if (mDialogClickCallback != null) {
                    mDialogClickCallback.onNo(remindDialog);
                } else {
                    remindDialog.dismiss();
                }
            });
            remindDialog.setContentView(customView);
            remindDialog.setCanceledOnTouchOutside(mOutsideCancelable);
            remindDialog.setCancelable(mCancelable);
            //设置dialog宽高属性
            WindowManager.LayoutParams attributes = remindDialog.getWindow().getAttributes();
            attributes.width = (int) (ScreenUtils.getScreenWidth() * 0.8);
            remindDialog.show();
            remindDialog.getWindow().setAttributes(attributes);
            return remindDialog;
        }
    }

    public interface DialogClickCallback {
        void onYes(Dialog dialog);

        void onNo(Dialog dialog);
    }
}
