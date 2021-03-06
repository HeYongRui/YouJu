package com.heyongrui.module.textword.contract;

import com.heyongrui.base.base.BasePresenter;
import com.heyongrui.base.base.BaseView;
import com.heyongrui.module.data.dto.HitokotoDto;

public interface HitokotoContract {
    interface View extends BaseView {
        void getDuJiTangSuccess(String dujitang);

        void getHitokotoSuccess(HitokotoDto hitokotoDto);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getDuJiTang();

        public abstract void getHitokoto(String c);
    }
}
