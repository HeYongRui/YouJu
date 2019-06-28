package com.heyongrui.module.kaiyan.contract;

import com.heyongrui.base.base.BasePresenter;
import com.heyongrui.base.base.BaseView;
import com.heyongrui.module.data.dto.KaiYanDto;

public interface KaiYanContract {
    interface View extends BaseView {

        void getKaiYanSuccess(KaiYanDto kaiYanDto);

        void requestFail(int errorCode, String errorMsg);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getRecommend();

        public abstract void getDiscovery();

        public abstract void getDaily();

        public abstract void getRelatedRecommend(int id);
    }
}
