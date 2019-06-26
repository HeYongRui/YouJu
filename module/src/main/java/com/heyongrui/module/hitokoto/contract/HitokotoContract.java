package com.heyongrui.module.hitokoto.contract;

import com.heyongrui.base.base.BasePresenter;
import com.heyongrui.base.base.BaseView;
import com.heyongrui.module.data.dto.DuJiTangDto;
import com.heyongrui.module.data.dto.HitokotoDto;

public interface HitokotoContract {
    interface View extends BaseView {
        void getDuJiTangSuccess(DuJiTangDto duJiTangDto);

        void getHitokotoSuccess(HitokotoDto hitokotoDto);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getDuJiTang();

        public abstract void getHitokoto(String c);
    }
}
