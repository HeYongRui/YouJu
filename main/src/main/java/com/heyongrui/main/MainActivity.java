package com.heyongrui.main;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.base.BaseActivity;

@Route(path = "/main/activity")
public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        addOnClickListeners(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.btn1) {
                    // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
                    ARouter.getInstance().build("/test/activity").navigation();
                } else if (id == R.id.btn2) {
                    // 2. 跳转并携带参数
                    Bundle bundle = new Bundle();
                    bundle.putString("test", "this is value");
                    ARouter.getInstance().build("/test/activity")
                            .withBundle("bundle", bundle)
                            .withString("key3", "888")
//                            .withObject("key4", true)
                            .navigation();
                }
            }
        }, R.id.btn1, R.id.btn2);
    }
}
