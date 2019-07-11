package com.heyongrui.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;

@Route(path = ConfigConstants.PATH_USER)
public class UserActivity extends BaseActivity {

    @Autowired
    Bundle bundle;
    @Autowired(name = "key3")
    String key;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Log.e(TAG, "key: " + key);
        if (bundle != null) {
            String test = bundle.getString("test");
            Log.e(TAG, "test: " + test);
        }
        addOnClickListeners(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.tv) {
//                    MiniPayUtils.setupPay(UserActivity.this, new Config.Builder("fkx00263qmoxalrjiwqgo21", R.drawable.ic_zhifubao, R.drawable.ic_weixin).build());
                }
            }
        }, R.id.tv);
    }
}
