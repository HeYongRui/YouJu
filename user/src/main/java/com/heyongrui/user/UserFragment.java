package com.heyongrui.user;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;

public class UserFragment extends BaseFragment {

    public static UserFragment getInstance() {
        UserFragment fragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tabIconId", R.drawable.icon_qingmang);
        bundle.putInt("tabTitleId", R.string.app_name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        addOnClickListeners(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.tv_user) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("test", "this is value");
//                    ARouter.getInstance().build(ConfigConstants.PATH_USER)
//                            .withBundle("bundle", bundle)
//                            .withString("key3", "888")
//                            .navigation();
                    ARouter.getInstance().build(ConfigConstants.PATH_KOTLIN).navigation();
                }
            }
        }, R.id.tv_user);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }
}
