package com.heyongrui.module.dailylife.contract;

import com.heyongrui.base.base.BasePresenter;
import com.heyongrui.base.base.BaseView;
import com.heyongrui.module.data.dto.GarbageCardBean;

import java.util.List;

public interface GarbageClassificationContract {
    interface View extends BaseView {
        void queryGarbageCategorySuccess(Object object);
    }

    abstract class Presenter extends BasePresenter<GarbageClassificationContract.View> {
        public abstract void queryGarbageCategory(String garbage);

        public abstract List<GarbageCardBean> getGargabeCardListData();
    }
}
