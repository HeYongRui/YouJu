package com.heyongrui.module2.about.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.utils.UiUtil;
import com.heyongrui.base.utils.UpdateCheckUtil;
import com.heyongrui.module2.R;

@Route(path = ConfigConstants.PATH_ABOUT)
public class AboutActivity extends BaseActivity {

    private UpdateCheckUtil mUpdateCheckUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        TextView tvVersion = findViewById(R.id.tv_version);
        tvVersion.setText(AppUtils.getAppVersionName());
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back) {
                finish();
            } else if (id == R.id.cl_author) {
                ARouter.getInstance().build(ConfigConstants.PATH_H5).withString("h5Url", "https://github.com/HeYongRui").navigation();
            } else if (id == R.id.cl_version) {
                if (mUpdateCheckUtil == null) {
                    mUpdateCheckUtil = new UpdateCheckUtil(this);
                }
                mUpdateCheckUtil.checkUpdate(getString(R.string.update_url), AppUtils.getAppPackageName() + ".fileProvider", getString(R.string.app_name));
            } else if (id == R.id.cl_encourage) {
                ARouter.getInstance().build(ConfigConstants.PATH_ENCOURAGE).navigation();
            }
        }, R.id.iv_back, R.id.cl_author, R.id.cl_version, R.id.cl_encourage);
        ImageView ivBack = findViewById(R.id.iv_back);
        Drawable drawable = DrawableUtil.tintDrawable(this, R.drawable.ic_back, ContextCompat.getColor(this, R.color.background));
        ivBack.setImageDrawable(drawable);
        UiUtil.setOnclickFeedBack(this, ContextCompat.getColor(this, R.color.background), ContextCompat.getColor(this, R.color.gray), findViewById(R.id.cl_author), findViewById(R.id.cl_version), findViewById(R.id.cl_encourage));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UpdateCheckUtil.REQUEST_CODE_APP_INSTALL) {
            if (mUpdateCheckUtil != null) {
                mUpdateCheckUtil.installApp(this, AppUtils.getAppPackageName() + ".fileProvider", getString(R.string.app_name));
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mUpdateCheckUtil != null) {
            mUpdateCheckUtil.cancelTask();
        }
        super.onDestroy();
    }
}
