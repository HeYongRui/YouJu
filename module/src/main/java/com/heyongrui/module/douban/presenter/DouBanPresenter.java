package com.heyongrui.module.douban.presenter;

import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.heyongrui.module.data.dto.DouBanDto;
import com.heyongrui.module.data.service.DouBanService;
import com.heyongrui.module.douban.contract.DouBanContract;
import com.heyongrui.network.configure.ResponseDisposable;

import java.util.ArrayList;
import java.util.List;

public class DouBanPresenter extends DouBanContract.Presenter {

    private DouBanService mDouBanService;

    public DouBanPresenter() {
        this.mDouBanService = new DouBanService();
    }

    @Override
    public void getIndexTags(String type, String source) {
        mRxManager.add(mDouBanService.getIndexTags(type, source)
                .subscribeWith(new ResponseDisposable<Object>(mContext, true) {
                    @Override
                    protected void onSuccess(Object object) {
                        String resultString = new Gson().toJson(object);
                        if (!TextUtils.isEmpty(resultString)) {
                            JsonObject jsonObject = new JsonParser().parse(resultString).getAsJsonObject();
                            if (jsonObject != null) {
                                boolean isHasTags = jsonObject.has("tags");
                                if (isHasTags) {
                                    JsonElement tags = jsonObject.get("tags");
                                    List<String> tagsList = new ArrayList<>();
                                    if (tags != null && tags.isJsonArray()) {
                                        JsonArray jsonArray = tags.getAsJsonArray();
                                        for (JsonElement jsonElement : jsonArray) {
                                            tagsList.add(jsonElement.getAsString());
                                        }
                                        if (mView != null) {
                                            mView.getIndexTagsSuccess(tagsList);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }

    @Override
    public void getSubjectsData(String type, String tag, String sort, int pageSize, int pageStart) {
        mRxManager.add(mDouBanService.getSubjectsData(type, tag, sort, pageSize, pageStart)
                .subscribeWith(new ResponseDisposable<DouBanDto>(mContext, true) {
                    @Override
                    protected void onSuccess(DouBanDto douBanDto) {
                        if (mView != null) {
                            mView.getSubjectsDataSuccess(douBanDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        if (mView != null) {
                            mView.getSubjectsDataFail(errorCode, errorMsg);
                        }
                    }
                }));
    }
}
