package com.heyongrui.module2.gank.contract;

import com.heyongrui.base.base.BasePresenter;
import com.heyongrui.base.base.BaseView;
import com.heyongrui.base.widget.imagewatcher.ImageWatcherHelper;
import com.heyongrui.module2.data.dto.GankDto;

import java.util.List;

public interface WelfareContract {
    interface View extends BaseView {
        void getWelfareSuccess(List<GankDto> gankDtoList);

        void getWelfareFail(int errorCode, String errorMsg);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract ImageWatcherHelper getImageWatcher();

        public abstract void getWelfare(int perPage, int page);
    }
}
