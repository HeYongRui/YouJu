package com.heyongrui.module.dailylife.presenter;

import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.module.R;
import com.heyongrui.module.dailylife.contract.GarbageClassificationContract;
import com.heyongrui.module.data.dto.GarbageCardBean;
import com.heyongrui.module.data.service.DailyLifeService;
import com.heyongrui.network.configure.ResponseDisposable;

import java.util.ArrayList;
import java.util.List;

public class GarbageClassificationPresenter extends GarbageClassificationContract.Presenter {

    private DailyLifeService mDailyLifeService;

    public GarbageClassificationPresenter() {
        mDailyLifeService = new DailyLifeService();
    }

    @Override
    public void queryGarbageCategory(String garbage) {
        if (TextUtils.isEmpty(garbage)) return;
        mRxManager.add(mDailyLifeService.getGarbageCategory(garbage).subscribeWith(
                new ResponseDisposable<Object>(mContext, true) {
                    @Override
                    protected void onSuccess(Object o) {
                        if (mView != null) {
                            mView.queryGarbageCategorySuccess(o);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }

    @Override
    public List<GarbageCardBean> getGargabeCardListData() {
        List<GarbageCardBean> garbageCardBeanList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            GarbageCardBean garbageCardBean = creeatGarbageBean(i);
            garbageCardBeanList.add(garbageCardBean);
        }
        return garbageCardBeanList;
    }

    private GarbageCardBean creeatGarbageBean(int category) {
        GarbageCardBean garbageCardBean = new GarbageCardBean();
        garbageCardBean.setCategory(category);
        switch (category) {
            case 1://可回收垃圾
                garbageCardBean.setIconUrl("http://www.atoolbox.net/Images/RefuseClassification/20190615142346.png");
                garbageCardBean.setConceptTitle(mContext.getString(R.string.recyclable_concept_title));
                garbageCardBean.setConceptContent(mContext.getString(R.string.recyclable_concept_content));
                garbageCardBean.setIncludeTitle(mContext.getString(R.string.recyclable_include_title));
                garbageCardBean.setIncludeContent(mContext.getString(R.string.recyclable_include_content));
                garbageCardBean.setStandardTitle(mContext.getString(R.string.recyclable_standard_title));
                garbageCardBean.setStandardContent(mContext.getString(R.string.recyclable_standard_content));
                break;
            case 2://干垃圾
                garbageCardBean.setIconUrl("http://www.atoolbox.net/Images/RefuseClassification/20190615142324.png");
                garbageCardBean.setConceptTitle(mContext.getString(R.string.dry_concept_title));
                garbageCardBean.setConceptContent(mContext.getString(R.string.dry_concept_content));
                garbageCardBean.setIncludeTitle(mContext.getString(R.string.dry_include_title));
                garbageCardBean.setIncludeContent(mContext.getString(R.string.dry_include_content));
                garbageCardBean.setStandardTitle(mContext.getString(R.string.dry_standard_title));
                garbageCardBean.setStandardContent(mContext.getString(R.string.dry_standard_content));
                break;
            case 3://湿垃圾
                garbageCardBean.setIconUrl("http://www.atoolbox.net/Images/RefuseClassification/20190615142410.png");
                garbageCardBean.setConceptTitle(mContext.getString(R.string.wet_concept_title));
                garbageCardBean.setConceptContent(mContext.getString(R.string.wet_concept_content));
                garbageCardBean.setIncludeTitle(mContext.getString(R.string.wet_include_title));
                garbageCardBean.setIncludeContent(mContext.getString(R.string.wet_include_content));
                garbageCardBean.setStandardTitle(mContext.getString(R.string.wet_standard_title));
                garbageCardBean.setStandardContent(mContext.getString(R.string.wet_standard_content));
                break;
            case 4://有害垃圾
                garbageCardBean.setIconUrl("http://www.atoolbox.net/Images/RefuseClassification/20190615142428.png");
                garbageCardBean.setConceptTitle(mContext.getString(R.string.harmful_concept_title));
                garbageCardBean.setConceptContent(mContext.getString(R.string.harmful_concept_content));
                garbageCardBean.setIncludeTitle(mContext.getString(R.string.harmful_include_title));
                garbageCardBean.setIncludeContent(mContext.getString(R.string.harmful_include_content));
                garbageCardBean.setStandardTitle(mContext.getString(R.string.harmful_standard_title));
                garbageCardBean.setStandardContent(mContext.getString(R.string.harmful_standard_content));
                break;
        }
        return garbageCardBean;
    }
}
