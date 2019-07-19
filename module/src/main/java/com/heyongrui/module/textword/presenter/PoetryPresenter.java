package com.heyongrui.module.textword.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.heyongrui.module.data.dto.PoetryDto;
import com.heyongrui.module.data.dto.TodayRecommendPoemDto;
import com.heyongrui.module.data.service.TextService;
import com.heyongrui.module.textword.contract.PoetryContract;
import com.heyongrui.network.configure.ResponseDisposable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class PoetryPresenter extends PoetryContract.Presenter {

    private TextService mTextService;

    public PoetryPresenter() {
        mTextService = new TextService();
    }


    @Override
    public void getPoetry() {
        mRxManager.add(mTextService.getPoetrySentence().subscribeWith(new ResponseDisposable<PoetryDto>(mContext, true) {
            @Override
            protected void onSuccess(PoetryDto poetryDto) {
                if (mView != null) {
                    mView.getPoetrySuccess(poetryDto);
                }
            }

            @Override
            protected void onFailure(int errorCode, String errorMsg) {
                if (mView != null) {
                    mView.getPoetryFail(errorCode, errorMsg);
                }
            }
        }));
    }

    @Override
    public void getTodayRecommendPoem() {
        mRxManager.add(Observable.zip(mTextService.getTodayRecommendCover(), mTextService.getTodayRecommendPoem(),
                (object, todayRecommendPoemDto) -> {
                    try {
                        if (object != null) {
                            String resultString = new Gson().toJson(object);
                            if (!TextUtils.isEmpty(resultString)) {
                                JsonObject jsonObject = new JsonParser().parse(resultString).getAsJsonObject();
                                JsonElement data = jsonObject.get("data");
                                if (data != null && data.isJsonObject()) {
                                    String url = data.getAsJsonObject().get("url").getAsString();
                                    todayRecommendPoemDto.setCover(url);
                                }
                            }
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                    return todayRecommendPoemDto;
                }).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new ResponseDisposable<TodayRecommendPoemDto>(mContext, true) {
            @Override
            protected void onSuccess(TodayRecommendPoemDto todayRecommendPoemDto) {
                if (mView != null) {
                    mView.getTodayRecommendPoemSuccess(todayRecommendPoemDto);
                }
            }

            @Override
            protected void onFailure(int errorCode, String errorMsg) {
                if (mView != null) {
                    mView.getTodayRecommendPoemFail(errorCode, errorMsg);
                }
            }
        }));
    }
}
