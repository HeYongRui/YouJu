package com.heyongrui.module.hitokoto.presenter;

import android.app.Dialog;

import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.base.widget.catloadingview.CatLoadingDialog;
import com.heyongrui.module.data.dto.DuJiTang2Dto;
import com.heyongrui.module.data.dto.DuJiTangDto;
import com.heyongrui.module.data.dto.HitokotoDto;
import com.heyongrui.module.data.service.HitokotoService;
import com.heyongrui.module.hitokoto.contract.HitokotoContract;
import com.heyongrui.network.configure.ResponseDisposable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HitokotoPresenter extends HitokotoContract.Presenter {

    private HitokotoService mHitokotoService;
    private Dialog mDuJiTangDialog;

    public HitokotoPresenter() {
        mHitokotoService = new HitokotoService();
    }

    private void showDuJiTangDialog() {
        if (mDuJiTangDialog == null) {
            mDuJiTangDialog = new CatLoadingDialog(mContext);
            mDuJiTangDialog.setCancelable(false);
            mDuJiTangDialog.setCanceledOnTouchOutside(false);
        }
        mDuJiTangDialog.show();
    }

    private void dismissDuJiTangDialog() {
        if (mDuJiTangDialog != null && mDuJiTangDialog.isShowing()) {
            mDuJiTangDialog.dismiss();
        }
    }

    @Override
    public void getDuJiTang() {
        showDuJiTangDialog();
        mRxManager.add(mHitokotoService.getDuJiTang().subscribeWith(
                new ResponseDisposable<DuJiTangDto>(mContext, false) {
                    @Override
                    protected void onSuccess(DuJiTangDto duJiTangDto) {
                        dismissDuJiTangDialog();
                        if (mView != null) {
                            mView.getDuJiTangSuccess(duJiTangDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        getDuJiTang2("https://api.qinor.cn/soup/", "https://ys.juan8014.cn/yiyan/api.php/");
                    }
                }));
    }

    private void getDuJiTang2(String... requestUrl) {
        if (requestUrl == null || requestUrl.length == 0) return;
        mRxManager.add(Observable.just(requestUrl).map(requestUrl1 -> {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .hostnameVerifier((hostname, session) -> true)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
            for (String url : requestUrl1) {
                try {
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    return data;
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
            return null;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResponseDisposable<String>(mContext, false) {
                    @Override
                    protected void onSuccess(String content) {
                        dismissDuJiTangDialog();
                        DuJiTangDto duJiTangDto = new DuJiTangDto();
                        duJiTangDto.setContent(content);
                        duJiTangDto.setAuthor("");
                        if (mView != null) {
                            mView.getDuJiTangSuccess(duJiTangDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        getDuJiTang3();
                    }
                }));
    }

    private void getDuJiTang3() {
        mRxManager.add(mHitokotoService.getDuJiTang2().subscribeWith(
                new ResponseDisposable<DuJiTang2Dto>(mContext, false) {
                    @Override
                    protected void onSuccess(DuJiTang2Dto duJiTang2Dto) {
                        dismissDuJiTangDialog();
                        if (duJiTang2Dto == null) return;
                        List<DuJiTang2Dto.DataBean> data = duJiTang2Dto.getData();
                        if (data == null || data.isEmpty()) return;
                        DuJiTang2Dto.DataBean dataBean = data.get(0);
                        if (dataBean == null) return;
                        String content = dataBean.getContent();
                        //将DuJiTang2Dto转换为DuJiTangDto并回调
                        DuJiTangDto duJiTangDto = new DuJiTangDto();
                        duJiTangDto.setContent(content);
                        duJiTangDto.setAuthor("");
                        if (mView != null) {
                            mView.getDuJiTangSuccess(duJiTangDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                        dismissDuJiTangDialog();
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
//    public static class ObservableIterable implements Iterable<Observable<String>> {
//        private List<Observable<String>> observableList;
//
//        public ObservableIterable() {
//            super();
//            observableList = new ArrayList<>();
//        }
//
//        public void add(Observable<String> value) {
//            if (observableList != null) {
//                observableList.add(value);
//            }
//        }
//
//        @Override
//        public Iterator<Observable<String>> iterator() {
//            return new Iterator<Observable<String>>() {
//                int index = -1;
//
//                @Override
//                public boolean hasNext() {
//                    return observableList.size() - 1 > index;
//                }
//
//                @Override
//                public Observable<String> next() {
//                    index++;
//                    return observableList.get(index);
//                }
//            };
//        }
//    }
//    ObservableIterable observables = new ObservableIterable();
//        observables.add(Observable.just(""));
//        Observable.zip(observables, new FuncN<Object>() {
//        @Override
//        public Object call(Object... args) {
//            return null;
//        }
//    });
}
