package com.heyongrui.module2.gank.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.heyongrui.base.glide.GlideApp;
import com.heyongrui.base.widget.imagewatcher.ImageWatcher;
import com.heyongrui.base.widget.imagewatcher.ImageWatcherHelper;
import com.heyongrui.module2.adapter.Module2SectionEntity;
import com.heyongrui.module2.data.dto.GankDto;
import com.heyongrui.module2.data.service.GankService;
import com.heyongrui.module2.gank.contract.WelfareContract;
import com.heyongrui.network.configure.ResponseDisposable;

public class WelfarePresenter extends WelfareContract.Presenter {

    private GankService mGankService;

    public WelfarePresenter() {
        mGankService = new GankService();
    }


    @Override
    public ImageWatcherHelper getImageWatcher() {
        if (mContext instanceof Activity) {
            return ImageWatcherHelper.with((Activity) mContext, new ImageWatcher.Loader<Object>() {
                @Override
                public void load(Context context, Object o, ImageWatcher.LoadCallback lc) {
                    if (o == null) return;
                    if (o instanceof Module2SectionEntity) {
                        GankDto.GankBean gankBean = ((Module2SectionEntity) o).getGankBean();
                        if (gankBean == null) return;
                        String url = gankBean.getUrl();
                        GlideApp.with(context).load(url)
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        lc.onResourceReady(resource);
                                    }

                                    @Override
                                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                        lc.onLoadFailed(errorDrawable);
                                    }

                                    @Override
                                    public void onLoadStarted(@Nullable Drawable placeholder) {
                                        lc.onLoadStarted(placeholder);
                                    }
                                });
                    }
                }
            });
        }
        return null;
    }

    @Override
    public void getWelfare(int perPage, int page) {
        mRxManager.add(mGankService.getGankCategory("福利", perPage, page).subscribeWith(
                new ResponseDisposable<GankDto>(mContext, true) {
                    @Override
                    protected void onSuccess(GankDto gankDto) {
                        if (mView != null) {
                            mView.getWelfareSuccess(gankDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        if (mView != null) {
                            mView.getWelfareFail(errorCode, errorMsg);
                        }
                    }
                }));
    }
}
