package com.heyongrui.module.kaiyan.contract;

import com.heyongrui.base.base.BasePresenter;
import com.heyongrui.base.base.BaseView;
import com.heyongrui.module.data.dto.KaiYanDto;

public interface KaiYanContract {
    interface View extends BaseView {

        void getRecommendSuccess(KaiYanDto kaiYanDto);

        void getDiscoverySuccess(KaiYanDto kaiYanDto);

        void getDailySuccess(KaiYanDto kaiYanDto);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getRecommend();

        public abstract void getDiscovery();

        public abstract void getDaily();
    }
}
