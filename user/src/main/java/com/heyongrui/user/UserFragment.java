package com.heyongrui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.utils.UiUtil;
import com.heyongrui.base.utils.UpdateCheckUtil;

public class UserFragment extends BaseFragment implements View.OnClickListener {

    private UpdateCheckUtil mUpdateCheckUtil;

    public static UserFragment getInstance() {
        UserFragment fragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tabIconId", R.drawable.ic_about);
        bundle.putInt("tabTitleId", R.string.about);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cl_author) {
            ARouter.getInstance().build(ConfigConstants.PATH_H5).withString("h5Url", "https://github.com/HeYongRui").navigation();
        } else if (id == R.id.cl_version) {
            if (mUpdateCheckUtil == null) {
                mUpdateCheckUtil = new UpdateCheckUtil(_mActivity);
            }
            mUpdateCheckUtil.checkUpdate(getString(R.string.update_url), AppUtils.getAppPackageName() + ".fileProvider", getString(R.string.app_name));
        } else if (id == R.id.cl_encourage) {
            ARouter.getInstance().build(ConfigConstants.PATH_ENCOURAGE).navigation();
        } else {
            Bundle bundle = new Bundle();
//                    bundle.putString("test", "this is value");
//                    ARouter.getInstance().build(ConfigConstants.PATH_USER)
//                            .withBundle("bundle", bundle)
//                            .withString("key3", "888")
//                            .navigation();
            ARouter.getInstance().build(ConfigConstants.PATH_USER).withBoolean(ConfigConstants.IS_NEED_INTERCEPT, true).navigation();
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        TextView tvVersion = mView.findViewById(R.id.tv_version);
        ConstraintLayout clAuthor = mView.findViewById(R.id.cl_author);
        ConstraintLayout clVersion = mView.findViewById(R.id.cl_version);
        ConstraintLayout clEncourage = mView.findViewById(R.id.cl_encourage);
        UiUtil.setOnclickFeedBack(ContextCompat.getColor(mContext, R.color.background),
                ContextCompat.getColor(mContext, R.color.gray), clAuthor, clVersion, clEncourage);

        tvVersion.setText(AppUtils.getAppVersionName());

        addOnClickListeners(this, clAuthor, clVersion, clEncourage);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UpdateCheckUtil.REQUEST_CODE_APP_INSTALL) {
            if (mUpdateCheckUtil != null) {
                mUpdateCheckUtil.installApp(_mActivity, AppUtils.getAppPackageName() + ".fileProvider", getString(R.string.app_name));
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mUpdateCheckUtil != null) {
            mUpdateCheckUtil.cancelTask();
        }
        super.onDestroy();
    }
}
