package com.heyongrui.module.mono.contract;

import com.heyongrui.base.base.BasePresenter;
import com.heyongrui.base.base.BaseView;
import com.heyongrui.base.widget.imagewatcher.ImageWatcherHelper;

public interface MonoTeaContract {
    interface View extends BaseView {

        void getTeaSuccess(Object object);

        void getTeaFail(int errorCode, String errorMsg);
    }

    abstract class Presenter extends BasePresenter<MonoTeaContract.View> {
        public abstract ImageWatcherHelper getImageWatcher();

        public abstract void getTea();

        public abstract void getHistoryTea(int id);

    }
}
