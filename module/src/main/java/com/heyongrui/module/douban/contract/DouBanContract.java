package com.heyongrui.module.douban.contract;

import com.heyongrui.base.base.BasePresenter;
import com.heyongrui.base.base.BaseView;
import com.heyongrui.module.data.dto.DouBanDto;

import java.util.List;

public interface DouBanContract {
    interface View extends BaseView {
        void getIndexTagsSuccess(List<String> tagsList);

        void getSubjectsDataSuccess(DouBanDto douBanDto);

        void getSubjectsDataFail(int errorCode, String errorMsg);
    }

    abstract class Presenter extends BasePresenter<DouBanContract.View> {

        public abstract void getIndexTags(String type, String source);

        public abstract void getSubjectsData(String type, String tag, String sort, int pageSize, int pageStart);
    }
}
