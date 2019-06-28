package com.heyongrui.module.kaiyan.presenter;

import com.heyongrui.module.data.dto.KaiYanDto;
import com.heyongrui.module.data.service.KaiYanService;
import com.heyongrui.module.kaiyan.contract.KaiYanContract;
import com.heyongrui.network.configure.ResponseDisposable;

public class KaiYanPresenter extends KaiYanContract.Presenter {

    private KaiYanService mKaiYanService;

    public KaiYanPresenter() {
        mKaiYanService = new KaiYanService();
    }

    @Override
    public void getRecommend() {
        mRxManager.add(mKaiYanService.getRecommend().subscribeWith(
                new ResponseDisposable<KaiYanDto>(mContext, true) {
                    @Override
                    protected void onSuccess(KaiYanDto kaiYanDto) {
                        if (mView != null) {
                            mView.getKaiYanSuccess(kaiYanDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        if (mView != null) {
                            mView.requestFail(errorCode, errorMsg);
                        }
                    }
                }));
    }

    @Override
    public void getDiscovery() {
        mRxManager.add(mKaiYanService.getDiscovery().subscribeWith(
                new ResponseDisposable<KaiYanDto>(mContext, true) {
                    @Override
                    protected void onSuccess(KaiYanDto kaiYanDto) {
                        if (mView != null) {
                            mView.getKaiYanSuccess(kaiYanDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        if (mView != null) {
                            mView.requestFail(errorCode, errorMsg);
                        }
                    }
                }));
    }

    @Override
    public void getDaily() {
        mRxManager.add(mKaiYanService.getDaily().subscribeWith(
                new ResponseDisposable<KaiYanDto>(mContext, true) {
                    @Override
                    protected void onSuccess(KaiYanDto kaiYanDto) {
                        if (mView != null) {
                            mView.getKaiYanSuccess(kaiYanDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        if (mView != null) {
                            mView.requestFail(errorCode, errorMsg);
                        }
                    }
                }));
    }

    @Override
    public void getRelatedRecommend(int id) {
        mRxManager.add(mKaiYanService.getRelatedRecommend(id).subscribeWith(
                new ResponseDisposable<KaiYanDto>(mContext, true) {
                    @Override
                    protected void onSuccess(KaiYanDto kaiYanDto) {
                        if (mView != null) {
                            mView.getKaiYanSuccess(kaiYanDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        if (mView != null) {
                            mView.requestFail(errorCode, errorMsg);
                        }
                    }
                }));
    }
}
