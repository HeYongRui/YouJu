package com.heyongrui.user.hitokoto.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.network.configure.ResponseDisposable;
import com.heyongrui.user.data.dto.DuJiTangDto;
import com.heyongrui.user.data.dto.HitokotoDto;
import com.heyongrui.user.data.service.HitokotoService;
import com.heyongrui.user.hitokoto.contract.HitokotoContract;

public class HitokotoPresenter extends HitokotoContract.Presenter {

    private HitokotoService mHitokotoService;

    public HitokotoPresenter() {
        mHitokotoService = new HitokotoService();
    }

    @Override
    public void getDuJiTang() {
        mRxManager.add(mHitokotoService.getDuJiTang().subscribeWith(
                new ResponseDisposable<DuJiTangDto>(mContext, true) {
                    @Override
                    protected void onSuccess(DuJiTangDto duJiTangDto) {
                        if (mView != null) {
                            mView.getDuJiTangSuccess(duJiTangDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }

    @Override
    public void getHitokoto(String c) {
        mRxManager.add(mHitokotoService.getHitokoto(c).subscribeWith(
                new ResponseDisposable<HitokotoDto>(mContext, true) {
                    @Override
                    protected void onSuccess(HitokotoDto hitokotoDto) {
                        if (mView != null) {
                            mView.getHitokotoSuccess(hitokotoDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }
}
