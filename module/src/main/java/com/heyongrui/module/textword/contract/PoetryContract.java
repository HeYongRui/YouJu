package com.heyongrui.module.textword.contract;

import com.heyongrui.base.base.BasePresenter;
import com.heyongrui.base.base.BaseView;
import com.heyongrui.module.data.dto.PoetryDto;
import com.heyongrui.module.data.dto.TodayRecommendPoemDto;

public interface PoetryContract {
    interface View extends BaseView {
        void getPoetrySuccess(PoetryDto poetryDto);

        void getPoetryFail(int errorCode, String errorMsg);

        void getTodayRecommendPoemSuccess(TodayRecommendPoemDto todayRecommendPoemDto);

        void getTodayRecommendPoemFail(int errorCode, String errorMsg);
    }

    abstract class Presenter extends BasePresenter<PoetryContract.View> {

        public abstract void getPoetry();

        public abstract void getTodayRecommendPoem();
    }
}
