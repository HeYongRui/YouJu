package com.heyongrui.module.textword.presenter;

import android.app.Dialog;

import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.base.widget.catloadingview.CatLoadingDialog;
import com.heyongrui.module.data.dto.HitokotoDto;
import com.heyongrui.module.data.service.TextService;
import com.heyongrui.module.textword.contract.HitokotoContract;
import com.heyongrui.network.configure.ResponseDisposable;
import com.heyongrui.network.configure.TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HitokotoPresenter extends HitokotoContract.Presenter {

    private TextService mTextService;
    private Dialog mDuJiTangDialog;

    public HitokotoPresenter() {
        mTextService = new TextService();
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
        String[] requestUrl = new String[]{"https://api.qinor.cn/soup/", "https://ys.juan8014.cn/yiyan/api.php/"};
        mRxManager.add(Observable.just(requestUrl).map(requestUrl1 -> {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .sslSocketFactory(TrustManager.getSSLSocketFactory(), TrustManager.trustManager)
                    .hostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
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
                    protected void onSuccess(String dujitang) {
                        dismissDuJiTangDialog();
                        if (mView != null) {
                            mView.getDuJiTangSuccess(dujitang);
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
        mRxManager.add(mTextService.getHitokoto(c).subscribeWith(
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
