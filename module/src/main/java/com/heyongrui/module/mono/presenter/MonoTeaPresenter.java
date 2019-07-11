package com.heyongrui.module.mono.presenter;

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
import com.heyongrui.module.data.dto.MonoTeaDto;
import com.heyongrui.module.data.service.MonoSerevice;
import com.heyongrui.module.mono.contract.MonoTeaContract;
import com.heyongrui.network.configure.ResponseDisposable;

public class MonoTeaPresenter extends MonoTeaContract.Presenter {

    private MonoSerevice mMonoSerevice;

    public MonoTeaPresenter() {
        mMonoSerevice = new MonoSerevice();
    }

    @Override
    public ImageWatcherHelper getImageWatcher() {
        if (mContext instanceof Activity) {
            return ImageWatcherHelper.with((Activity) mContext, new ImageWatcher.Loader<Object>() {
                @Override
                public void load(Context context, Object o, ImageWatcher.LoadCallback lc) {
                    if (o == null) return;
                    if (o instanceof MonoTeaDto.EntityListBean.MeowBean.ThumbBean) {
                        String raw = ((MonoTeaDto.EntityListBean.MeowBean.ThumbBean) o).getRaw();
                        GlideApp.with(context).load(raw)
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
    public void getTea() {
        mRxManager.add(mMonoSerevice.getTea().subscribeWith(
                new ResponseDisposable<MonoTeaDto>(mContext, true) {
                    @Override
                    protected void onSuccess(MonoTeaDto monoTeaDto) {
                        if (mView != null) {
                            mView.getTeaSuccess(monoTeaDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        if (mView != null) {
                            mView.getTeaFail(errorCode, errorMsg);
                        }
                    }
                }));
    }

    @Override
    public void getHistoryTea(int id) {
        mRxManager.add(mMonoSerevice.getHistoryTea(id).subscribeWith(
                new ResponseDisposable<Object>(mContext, true) {
                    @Override
                    protected void onSuccess(Object o) {
                        if (mView != null) {
                            mView.getTeaSuccess(o);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        if (mView != null) {
                            mView.getTeaFail(errorCode, errorMsg);
                        }
                    }
                }));
    }
}
