package com.heyongrui.module.textword.view;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.assist.RxManager;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration;
import com.heyongrui.module.R;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.data.dto.PoemSearchDto;
import com.heyongrui.module.data.service.TextService;
import com.heyongrui.network.configure.ResponseDisposable;

import java.util.ArrayList;
import java.util.List;

public class PoetrySearchFragment extends BaseFragment {

    private int mType;//搜索类型 1-原文 2-标题 4-作者
    private ModuleSectionAdapter mPoetryAdapter;
    private TextService mTextService;
    private RxManager mRxManager;


    public static PoetrySearchFragment getInstance(int type) {
        PoetrySearchFragment fragment = new PoetrySearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_poetry_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        RecyclerView recyclerView = mView.findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt("type");
        }
        //初始化数据
        mTextService = new TextService();
        mRxManager = new RxManager();
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        List<ModuleSectionEntity> data = new ArrayList<>();
        mPoetryAdapter = new ModuleSectionAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(manager);
        mPoetryAdapter.bindToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecycleViewItemDecoration(mContext, ConvertUtils.dp2px(1)));
        mPoetryAdapter.setOnItemClickListener((adapter, view, position) -> {
            PoemSearchDto.DataBean.PoemSearchBean poemSearchBean = mPoetryAdapter.getData().get(position).getPoemSearchBean();
            if (poemSearchBean != null) {
                ARouter.getInstance().build(ConfigConstants.PATH_POETRY_DETAIL).withInt("id", poemSearchBean.getId()).navigation();
            }
        });
    }

    public void searchPoem(String keywords, boolean isShowLoading) {
        mRxManager.add(mTextService.searchPoem(keywords, mType)
                .subscribeWith(new ResponseDisposable<PoemSearchDto>(mContext, isShowLoading) {
                    @Override
                    protected void onSuccess(PoemSearchDto poemSearchDto) {
                        List<ModuleSectionEntity> newDataList = new ArrayList<>();
                        if (poemSearchDto != null) {
                            PoemSearchDto.DataBean dataBean = poemSearchDto.getData();
                            if (dataBean != null) {
                                List<PoemSearchDto.DataBean.PoemSearchBean> searchBeanList = dataBean.getData();
                                if (searchBeanList != null) {
                                    for (PoemSearchDto.DataBean.PoemSearchBean poemSearchBean : searchBeanList) {
                                        newDataList.add(new ModuleSectionEntity(ModuleSectionEntity.TODAY_POEM, poemSearchBean));
                                    }
                                }
                            }
                        }
                        mPoetryAdapter.replaceData(newDataList);
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }

    @Override
    public void onDestroy() {
        if (mRxManager != null) {
            mRxManager.clear();
        }
        mTextService = null;
        super.onDestroy();
    }
}
