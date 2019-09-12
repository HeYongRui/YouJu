package com.heyongrui.main.mob.presenter;

import android.graphics.drawable.ShapeDrawable;
import android.text.TextUtils;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.heyongrui.base.app.BaseApplication;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.main.R;
import com.heyongrui.main.adapter.HomeSectionAdapter;
import com.heyongrui.main.adapter.HomeSectionEntity;
import com.heyongrui.main.dagger.DaggerHomeComponent;
import com.heyongrui.main.dagger.HomeModule;
import com.heyongrui.main.data.dto.DictionaryDto;
import com.heyongrui.main.data.dto.FlightDto;
import com.heyongrui.main.data.dto.IDCardDto;
import com.heyongrui.main.data.dto.IdiomDto;
import com.heyongrui.main.data.dto.MobResponse;
import com.heyongrui.main.data.service.MobService;
import com.heyongrui.main.mob.contract.MobContract;
import com.heyongrui.network.configure.ResponseDisposable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MobPresenter extends MobContract.Presenter {

    @Inject
    MobService mMobService;

    public MobPresenter() {
        DaggerHomeComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .homeModule(new HomeModule())
                .build().inject(this);
    }


    @Override
    public void setBorderBg(View... views) {
        for (View view : views) {
            ShapeDrawable borderEnableBg = new DrawableUtil.DrawableBuilder(mContext).setRingOutRadius(10)
                    .setRingInnerRadius(8).setRingSpaceOutAndInner(2)
                    .setColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark)).createRingDrawable();
            ViewCompat.setBackground(view, borderEnableBg);
        }
    }

    @Override
    public HomeSectionAdapter initRecyclerView(RecyclerView recyclerView, BaseQuickAdapter.OnItemClickListener listener) {
        List<HomeSectionEntity> data = new ArrayList<>();
        HomeSectionAdapter moduleSectionAdapter = new HomeSectionAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        moduleSectionAdapter.bindToRecyclerView(recyclerView);
//        int dp10 = ConvertUtils.dp2px(10);
//        recyclerView.addItemDecoration(new RecycleViewItemDecoration(mContext));
//        moduleSectionAdapter.setSpanSizeLookup((gridLayoutManager, position) -> data.get(position).getSpanSize());
        if (null != listener) {
            moduleSectionAdapter.setOnItemClickListener(listener);
        }
        return moduleSectionAdapter;
    }

    @Override
    public void flightInfoQuery(String start, String end, String flightNo) {
        if (TextUtils.isEmpty(flightNo)) {//如果航班号为空，执行根据起落城市名或者机场代码查询航班信息
            mRxManager.add(mMobService.flightQuery(start, end)
                    .subscribeWith(new ResponseDisposable<MobResponse<List<FlightDto>>>(mContext, true) {
                        @Override
                        protected void onSuccess(MobResponse<List<FlightDto>> mobResponse) {
                            if (null != mView) {
                                List<FlightDto> flightDtoList = null;
                                if (null != mobResponse) {
                                    if (mobResponse.getRetCode() == 200) {
                                        flightDtoList = mobResponse.getResult();
                                    } else {
                                        ToastUtils.showShort(mobResponse.getMsg());
                                    }
                                }
                                mView.flightInfoQuerySuccess(flightDtoList);
                            }
                        }

                        @Override
                        protected void onFailure(int errorCode, String errorMsg) {
                            ToastUtils.showShort(errorMsg);
                        }
                    }));
        } else {//如果航班号不为空，执行根据航班号查询航班信息
            mRxManager.add(mMobService.flightNoQuery(flightNo)
                    .subscribeWith(new ResponseDisposable<MobResponse<List<FlightDto>>>(mContext, true) {
                        @Override
                        protected void onSuccess(MobResponse<List<FlightDto>> mobResponse) {
                            if (null != mView) {
                                List<FlightDto> flightDtoList = null;
                                if (null != mobResponse) {
                                    if (mobResponse.getRetCode() == 200) {
                                        flightDtoList = mobResponse.getResult();
                                    } else {
                                        ToastUtils.showShort(mobResponse.getMsg());
                                    }
                                }
                                mView.flightInfoQuerySuccess(flightDtoList);
                            }
                        }

                        @Override
                        protected void onFailure(int errorCode, String errorMsg) {
                            ToastUtils.showShort(errorMsg);
                        }
                    }));
        }
    }

    @Override
    public void idCardQuery(String word) {
        mRxManager.add(mMobService.idCardQuery(word)
                .subscribeWith(new ResponseDisposable<MobResponse<IDCardDto>>(mContext, true) {
                    @Override
                    protected void onSuccess(MobResponse<IDCardDto> idCardDtoMobResponse) {
                        if (null != mView) {
                            IDCardDto idCardDto = null;
                            if (null != idCardDtoMobResponse) {
                                if (idCardDtoMobResponse.getRetCode() == 200) {
                                    idCardDto = idCardDtoMobResponse.getResult();
                                } else {
                                    ToastUtils.showShort(idCardDtoMobResponse.getMsg());
                                }
                            }
                            mView.idCardQuerySuccess(idCardDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }

    @Override
    public void dictionaryQuery(String word) {
        mRxManager.add(mMobService.dictionaryQuery(word)
                .subscribeWith(new ResponseDisposable<MobResponse<DictionaryDto>>(mContext, true) {
                    @Override
                    protected void onSuccess(MobResponse<DictionaryDto> dictionaryDtoMobResponse) {
                        if (null != mView) {
                            DictionaryDto dictionaryDto = null;
                            if (null != dictionaryDtoMobResponse) {
                                if (dictionaryDtoMobResponse.getRetCode() == 200) {
                                    dictionaryDto = dictionaryDtoMobResponse.getResult();
                                } else {
                                    ToastUtils.showShort(dictionaryDtoMobResponse.getMsg());
                                }
                            }
                            mView.dictionaryQuerySuccess(dictionaryDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }

    @Override
    public void idiomQuery(String idiomStr) {
        mRxManager.add(mMobService.idiomQuery(idiomStr)
                .subscribeWith(new ResponseDisposable<MobResponse<IdiomDto>>(mContext, true) {
                    @Override
                    protected void onSuccess(MobResponse<IdiomDto> idiomDtoMobResponse) {
                        if (null != mView) {
                            IdiomDto idiomDto = null;
                            if (null != idiomDtoMobResponse) {
                                if (idiomDtoMobResponse.getRetCode() == 200) {
                                    idiomDto = idiomDtoMobResponse.getResult();
                                } else {
                                    ToastUtils.showShort(idiomDtoMobResponse.getMsg());
                                }
                            }
                            mView.idiomQuerySuccess(idiomDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }

    @Override
    public void participleParse(String originStr) {
        mRxManager.add(mMobService.participleParse(originStr)
                .subscribeWith(new ResponseDisposable<MobResponse<List<String>>>(mContext, true) {
                    @Override
                    protected void onSuccess(MobResponse<List<String>> listMobResponse) {
                        if (null != mView) {
                            List<String> stringList = null;
                            if (null != listMobResponse) {
                                if (listMobResponse.getRetCode() == 200) {
                                    stringList = listMobResponse.getResult();
                                } else {
                                    ToastUtils.showShort(listMobResponse.getMsg());
                                }
                            }
                            mView.participleParseSuccess(stringList);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }
}
