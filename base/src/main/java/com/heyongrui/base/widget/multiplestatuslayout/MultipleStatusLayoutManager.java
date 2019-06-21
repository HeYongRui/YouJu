package com.heyongrui.base.widget.multiplestatuslayout;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;

import com.heyongrui.base.R;
import com.heyongrui.base.utils.GlideUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lambert on 2018/10/17.
 */

public class MultipleStatusLayoutManager {
    public final int NO_LAYOUT_ID = 0;
    public final int DEFAULT_LOADING_LAYOUT_ID = R.layout.layout_loading_view;
    public final int DEFAULT_ERROR_LAYOUT_ID = R.layout.layout_error_view;
    public final int DEFAULT_NONETWORK_LAYOUT_ID = R.layout.layout_nonetwork_view;
    public final int DEFAULT_EMPTY_LAYOUT_ID = R.layout.layout_empty_view;
    public final int DEFAULT_RETRY_LAYOUT_ID = R.layout.layout_retry_view;

    private View mLoadingView;
    private ImageView mLoadIv;
    private View mErrorView;
    private View mNoNetworkView;
    private View mEmptyView;
    private View mRetryView;

    private int mLoadingLayoutId;
    private int mErrorLayoutId;
    private int mNoNetworkLayoutId;
    private int mEmptyLayoutId;
    private int mRetryLayoutId;

    private MultipleStatusLayout mMultipleStatusLayout;
    private HashMap<String, View.OnClickListener> listenerHashMap = new HashMap<>();
    private HashMap<String, View.OnClickListener> childListenerHashMap = new HashMap<>();
    private HashMap<String, List<Integer>> childIdsHashMap = new HashMap<>();

    public static MultipleStatusLayoutManager generate(Object activityOrFragmentOrView) {
        return new MultipleStatusLayoutManager(activityOrFragmentOrView);
    }

    public MultipleStatusLayoutManager(Object activityOrFragmentOrView) {
        ViewGroup contentParent = null;
        Context context;
        if (activityOrFragmentOrView instanceof Activity) {
            Activity activity = (Activity) activityOrFragmentOrView;
            context = activity;
            contentParent = activity.findViewById(android.R.id.content);
        } else if (activityOrFragmentOrView instanceof Fragment) {
            Fragment fragment = (Fragment) activityOrFragmentOrView;
            context = fragment.getActivity();
            contentParent = (ViewGroup) (fragment.getView().getParent());
        } else if (activityOrFragmentOrView instanceof View) {
            View view = (View) activityOrFragmentOrView;
            contentParent = (ViewGroup) (view.getParent());
            context = view.getContext();
        } else {
            throw new IllegalArgumentException("the argument's type must be Fragment or Activity or View");
        }
        int childCount = contentParent.getChildCount();
        //get contentParent
        int index = 0;
        View oldContent;
        if (activityOrFragmentOrView instanceof View) {
            oldContent = (View) activityOrFragmentOrView;
            for (int i = 0; i < childCount; i++) {
                if (contentParent.getChildAt(i) == oldContent) {
                    index = i;
                    break;
                }
            }
        } else {
            oldContent = contentParent.getChildAt(0);
        }
        contentParent.removeView(oldContent);
        //setup content layout
        MultipleStatusLayout multipleStatusLayout = new MultipleStatusLayout(context);

        ViewGroup.LayoutParams lp = oldContent.getLayoutParams();
        contentParent.addView(multipleStatusLayout, index, lp);
        multipleStatusLayout.setContentView(oldContent);

        mMultipleStatusLayout = multipleStatusLayout;
    }

    private View setLoadingView(MultipleStatusLayout multipleStatusLayout) {
        View loadingView = null;
        if (mLoadingLayoutId != NO_LAYOUT_ID) {//优先自定义布局
            loadingView = multipleStatusLayout.setLoadingView(mLoadingLayoutId);
        } else if (mLoadingView != null) {//其次自定义视图
            loadingView = multipleStatusLayout.setLoadingView(mLoadingView);
        } else if (DEFAULT_LOADING_LAYOUT_ID != NO_LAYOUT_ID) {//最后全局公共
            loadingView = multipleStatusLayout.setLoadingView(DEFAULT_LOADING_LAYOUT_ID);
        }
        return loadingView;
    }

    private View setErrorView(MultipleStatusLayout multipleStatusLayout) {
        View errorView = null;
        if (mErrorLayoutId != NO_LAYOUT_ID) {//优先自定义布局
            errorView = multipleStatusLayout.setErrorView(mErrorLayoutId);
        } else if (mErrorView != null) {//其次自定义视图
            errorView = multipleStatusLayout.setErrorView(mErrorView);
        } else if (DEFAULT_ERROR_LAYOUT_ID != NO_LAYOUT_ID) {//最后全局公共
            errorView = multipleStatusLayout.setErrorView(DEFAULT_ERROR_LAYOUT_ID);
        }
        //设置事件监听器
        setRootListener(errorView, "errorListener");
        //设置子view事件监听器
        setChildListener(errorView, "errorChildIds", "errorChildListener");
        return errorView;
    }

    private View setNoNetworkView(MultipleStatusLayout multipleStatusLayout) {
        View noNetworkView = null;
        if (mNoNetworkLayoutId != NO_LAYOUT_ID) {//优先自定义布局
            noNetworkView = multipleStatusLayout.setNoNetworkView(mNoNetworkLayoutId);
        } else if (mNoNetworkView != null) {//其次自定义视图
            noNetworkView = multipleStatusLayout.setNoNetworkView(mNoNetworkView);
        } else if (DEFAULT_NONETWORK_LAYOUT_ID != NO_LAYOUT_ID) {//最后全局公共
            noNetworkView = multipleStatusLayout.setNoNetworkView(DEFAULT_NONETWORK_LAYOUT_ID);
        }
        //设置事件监听器
        setRootListener(noNetworkView, "noNetworkListener");
        //设置子view事件监听器
        setChildListener(noNetworkView, "noNetworkChildIds", "noNetworkChildListener");
        return noNetworkView;
    }

    private View setEmptyView(MultipleStatusLayout multipleStatusLayout) {
        View emptyView = null;
        if (mEmptyLayoutId != NO_LAYOUT_ID) {//优先自定义布局
            emptyView = multipleStatusLayout.setEmptyView(mEmptyLayoutId);
        } else if (mEmptyView != null) {//其次自定义视图
            emptyView = multipleStatusLayout.setEmptyView(mEmptyView);
        } else if (DEFAULT_EMPTY_LAYOUT_ID != NO_LAYOUT_ID) {//最后全局公共
            emptyView = multipleStatusLayout.setEmptyView(DEFAULT_EMPTY_LAYOUT_ID);
        }
        //设置事件监听器
        setRootListener(emptyView, "emptyListener");
        //设置子view事件监听器
        setChildListener(emptyView, "emptyChildIds", "emptyChildListener");
        return emptyView;
    }

    private View setRetryView(MultipleStatusLayout multipleStatusLayout) {
        View retryView = null;
        if (mRetryLayoutId != NO_LAYOUT_ID) {//优先自定义布局
            retryView = multipleStatusLayout.setRetryView(mRetryLayoutId);
        } else if (mRetryView != null) {//其次自定义视图
            retryView = multipleStatusLayout.setRetryView(mRetryView);
        } else if (DEFAULT_RETRY_LAYOUT_ID != NO_LAYOUT_ID) {//最后全局公共
            retryView = multipleStatusLayout.setRetryView(DEFAULT_RETRY_LAYOUT_ID);
        }
        return retryView;
    }

    private void setRootListener(View rootView, String rootListenerKey) {
        if (rootView == null || TextUtils.isEmpty(rootListenerKey)) return;
        View.OnClickListener clickListener = listenerHashMap.get(rootListenerKey);
        if (clickListener == null) return;
        rootView.setOnClickListener(clickListener);
    }

    private void setChildListener(View rootView, String childIdsKey, String childListenerKey) {
        if (rootView == null || TextUtils.isEmpty(childIdsKey) || TextUtils.isEmpty(childListenerKey))
            return;
        List<Integer> childIds = childIdsHashMap.get(childIdsKey);
        if (childIds == null || childIds.isEmpty()) return;
        View.OnClickListener childListener = childListenerHashMap.get(childListenerKey);
        if (childListener == null) return;
        for (Integer childId : childIds) {
            View childView = rootView.findViewById(childId);
            if (childView == null) continue;
            childView.setOnClickListener(childListener);
        }
    }

    public void showLoading() {
        View loadingView = mMultipleStatusLayout.getLoadingView();
        if (loadingView == null) {
            View view = setLoadingView(mMultipleStatusLayout);
            mLoadIv = view.findViewById(R.id.load_iv);
        }
        setLoadingViewStatus(true);
        mMultipleStatusLayout.showLoading();
    }

    private void setLoadingViewStatus(boolean isShow) {
        if (mLoadIv == null) return;
        if (isShow) {
            GlideUtil.loadGif(mLoadIv.getContext(), android.R.drawable.ic_menu_rotate, mLoadIv, -1, null, null);
        } else {
            mLoadIv.setImageDrawable(null);
        }
    }

    public void showError(@DrawableRes int errorRes, CharSequence errorMsg) {
        setLoadingViewStatus(false);
        View errorView = mMultipleStatusLayout.getErrorView();
        if (errorView == null) {
            errorView = setErrorView(mMultipleStatusLayout);
        }
        if (errorView != null) {
            ImageView errorIv = errorView.findViewById(R.id.error_iv);
            TextView errorTv = errorView.findViewById(R.id.error_tv);
            if (errorIv != null && errorTv != null) {
                errorIv.setImageResource(errorRes);
                errorTv.setText(errorMsg);
            }
        }
        mMultipleStatusLayout.showError();
    }

    public void showNoNetwork(@DrawableRes int noNetRes, CharSequence noNetMsg) {
        setLoadingViewStatus(false);
        View noNetworkView = mMultipleStatusLayout.getNoNetworkView();
        if (noNetworkView == null) {
            setNoNetworkView(mMultipleStatusLayout);
        }
        if (noNetworkView != null) {
            ImageView noNetIv = noNetworkView.findViewById(R.id.nonetwork_iv);
            TextView noNetTv = noNetworkView.findViewById(R.id.nonetwork_tv);
            if (noNetIv != null && noNetTv != null) {
                noNetIv.setImageResource(noNetRes);
                noNetTv.setText(noNetMsg);
            }
        }
        mMultipleStatusLayout.showNoNetworkView();
    }

    public void showEmpty(@DrawableRes int emptyRes, CharSequence emptyMsg) {
        setLoadingViewStatus(false);
        View emptyView = mMultipleStatusLayout.getEmptyView();
        if (emptyView == null) {
            emptyView = setEmptyView(mMultipleStatusLayout);
        }
        ImageView emptyIv = emptyView.findViewById(R.id.empty_iv);
        if (emptyIv != null) {
            emptyIv.setImageResource(emptyRes);
        }
        TextView emptyTv = emptyView.findViewById(R.id.empty_tv);
        if (emptyTv != null) {
            emptyTv.setMovementMethod(LinkMovementMethod.getInstance());
            emptyTv.setText(emptyMsg);
        }
        mMultipleStatusLayout.showEmpty();
    }

    public void showRetry() {
        setLoadingViewStatus(false);
        View retryView = mMultipleStatusLayout.getRetryView();
        if (retryView == null) {
            setRetryView(mMultipleStatusLayout);
        }
        mMultipleStatusLayout.showRetry();
    }

    public void showContent() {
        setLoadingViewStatus(false);
        mMultipleStatusLayout.showContent();
    }

    public void setErrorListener(View.OnClickListener errorListener) {
        if (errorListener == null) return;
        listenerHashMap.put("errorListener", errorListener);
    }

    public void setNoNetworkListener(View.OnClickListener noNetworkListener) {
        if (noNetworkListener == null) return;
        listenerHashMap.put("noNetworkListener", noNetworkListener);
    }

    public void setEmptyListener(View.OnClickListener emptyListener) {
        if (emptyListener == null) return;
        listenerHashMap.put("emptyListener", emptyListener);
    }

    public void setErrorChildListener(View.OnClickListener errorChildListener, @IdRes int... childIds) {
        if (childIds == null || errorChildListener == null) return;
        List<Integer> errorChildIds = new ArrayList<>();
        for (int childId : childIds) {
            errorChildIds.add(childId);
        }
        childIdsHashMap.put("errorChildIds", errorChildIds);
        childListenerHashMap.put("errorChildListener", errorChildListener);
    }

    public void setNoNetworkChildListener(View.OnClickListener noNetworkChildListener, @IdRes int... childIds) {
        if (childIds == null || noNetworkChildListener == null) return;
        List<Integer> noNetworkChildIds = new ArrayList<>();
        for (int childId : childIds) {
            noNetworkChildIds.add(childId);
        }
        childIdsHashMap.put("noNetworkChildIds", noNetworkChildIds);
        childListenerHashMap.put("noNetworkChildListener", noNetworkChildListener);
    }

    public void setEmptyChildListener(View.OnClickListener emptyChildListener, @IdRes int... childIds) {
        if (childIds == null || emptyChildListener == null) return;
        List<Integer> emptyChildIds = new ArrayList<>();
        for (int childId : childIds) {
            emptyChildIds.add(childId);
        }
        childIdsHashMap.put("emptyChildIds", emptyChildIds);
        childListenerHashMap.put("emptyChildListener", emptyChildListener);
    }

    public MultipleStatusLayoutManager setLoadingView(View loadingView) {
        this.mLoadingView = loadingView;
        return this;
    }

    public MultipleStatusLayoutManager setErrorView(View errorView) {
        this.mErrorView = errorView;
        return this;
    }

    public MultipleStatusLayoutManager setNoNetworkView(View noNetworkView) {
        this.mNoNetworkView = noNetworkView;
        return this;
    }

    public MultipleStatusLayoutManager setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        return this;
    }

    public MultipleStatusLayoutManager setRetryView(View retryView) {
        this.mRetryView = retryView;
        return this;
    }

    public MultipleStatusLayoutManager setLoadingLayoutId(int loadingLayoutId) {
        this.mLoadingLayoutId = loadingLayoutId;
        return this;
    }

    public MultipleStatusLayoutManager setErrorLayoutId(int errorLayoutId) {
        this.mErrorLayoutId = errorLayoutId;
        return this;
    }

    public MultipleStatusLayoutManager setNoNetworkLayoutId(int noNetworkLayoutId) {
        this.mNoNetworkLayoutId = noNetworkLayoutId;
        return this;
    }

    public MultipleStatusLayoutManager setEmptyLayoutId(int emptyLayoutId) {
        this.mEmptyLayoutId = emptyLayoutId;
        return this;
    }

    public MultipleStatusLayoutManager setRetryLayoutId(int retryLayoutId) {
        this.mRetryLayoutId = retryLayoutId;
        return this;
    }
}
