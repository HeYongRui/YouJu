package com.heyongrui.module.kaiyan.view;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.module.R;
import com.heyongrui.module.data.dto.KaiYanDto;
import com.heyongrui.module.kaiyan.contract.KaiYanContract;
import com.heyongrui.module.kaiyan.presenter.KaiYanPresenter;

@Route(path = ConfigConstants.PATH_KAIYAN)
public class KaiYanActivity extends BaseActivity<KaiYanContract.Presenter> implements KaiYanContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_kaiyan;
    }

    @Override
    protected KaiYanContract.Presenter setPresenter() {
        return new KaiYanPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        addOnClickListeners(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.iv_back) {
                    finish();
                }
            }
        }, R.id.iv_back);
    }

    @Override
    public void getRecommendSuccess(KaiYanDto kaiYanDto) {

    }

    @Override
    public void getDiscoverySuccess(KaiYanDto kaiYanDto) {

    }

    @Override
    public void getDailySuccess(KaiYanDto kaiYanDto) {

    }
}
